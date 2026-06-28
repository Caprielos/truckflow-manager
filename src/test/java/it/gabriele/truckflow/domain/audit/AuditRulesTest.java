package it.gabriele.truckflow.domain.audit;

import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa AuditRules.
 */
class AuditRulesTest {

    @Test
    void shouldAllowAppendingChronologicalEventForSameAggregate() {
        AuditTrail trail = AuditTrail.of(
                event("AUD-001", AuditActionType.CREATED, "2026-07-01T08:00:00Z")
        );

        AuditEvent nextEvent = event(
                "AUD-002",
                AuditActionType.STATUS_CHANGED,
                "2026-07-01T09:00:00Z"
        );

        assertTrue(AuditRules.canAppendEvent(trail, nextEvent));
    }

    @Test
    void shouldRejectAppendingEventBeforeLatestEvent() {
        AuditTrail trail = AuditTrail.of(
                event("AUD-001", AuditActionType.CREATED, "2026-07-01T08:00:00Z"),
                event("AUD-002", AuditActionType.UPDATED, "2026-07-01T10:00:00Z")
        );

        AuditEvent oldEvent = event(
                "AUD-003",
                AuditActionType.STATUS_CHANGED,
                "2026-07-01T09:00:00Z"
        );

        assertFalse(AuditRules.canAppendEvent(trail, oldEvent));
    }

    @Test
    void shouldRejectDuplicatedEventId() {
        AuditTrail trail = AuditTrail.of(
                event("AUD-001", AuditActionType.CREATED, "2026-07-01T08:00:00Z")
        );

        AuditEvent duplicated = event(
                "AUD-001",
                AuditActionType.UPDATED,
                "2026-07-01T09:00:00Z"
        );

        assertFalse(AuditRules.canAppendEvent(trail, duplicated));
    }

    @Test
    void shouldRejectDifferentAggregate() {
        AuditTrail trail = AuditTrail.of(
                event("AUD-001", AuditActionType.CREATED, "2026-07-01T08:00:00Z")
        );

        AuditEvent differentAggregate = AuditEvent.userAction(
                "AUD-002",
                "INVOICE",
                "INV-001",
                "USER-001",
                AuditActionType.CREATED,
                Instant.parse("2026-07-01T09:00:00Z"),
                Notes.empty()
        );

        assertFalse(AuditRules.canAppendEvent(trail, differentAggregate));
    }

    @Test
    void shouldDetectReviewSecurityAndFinancialEvents() {
        AuditEvent warning = AuditEvent.of(
                "AUD-001",
                "SHIPMENT",
                "SHP-001",
                AuditActorType.USER,
                "USER-001",
                AuditActionType.UPDATED,
                AuditSeverity.WARNING,
                Instant.parse("2026-07-01T08:00:00Z"),
                Notes.empty()
        );

        AuditTrail trail = AuditTrail.of(
                warning,
                event("AUD-002", AuditActionType.PAYMENT_REGISTERED, "2026-07-01T09:00:00Z"),
                AuditEvent.userAction(
                        "AUD-003",
                        "SHIPMENT",
                        "SHP-001",
                        "USER-001",
                        AuditActionType.LOGIN_FAILED,
                        Instant.parse("2026-07-01T10:00:00Z"),
                        Notes.empty()
                )
        );

        assertTrue(AuditRules.requiresReview(warning));
        assertTrue(AuditRules.requiresReview(trail));
        assertTrue(AuditRules.containsFinancialImpactEvents(trail));
        assertTrue(AuditRules.containsSecuritySensitiveEvents(trail));
    }

    @Test
    void shouldConfirmTrailIsChronological() {
        AuditTrail trail = AuditTrail.of(
                event("AUD-002", AuditActionType.UPDATED, "2026-07-01T10:00:00Z"),
                event("AUD-001", AuditActionType.CREATED, "2026-07-01T08:00:00Z")
        );

        assertTrue(AuditRules.isChronological(trail));
    }

    @Test
    void shouldNotAllowNullValues() {
        AuditTrail trail = AuditTrail.of(
                event("AUD-001", AuditActionType.CREATED, "2026-07-01T08:00:00Z")
        );

        AuditEvent event = event("AUD-002", AuditActionType.UPDATED, "2026-07-01T09:00:00Z");

        assertThrows(IllegalArgumentException.class, () -> AuditRules.canAppendEvent(null, event));
        assertThrows(IllegalArgumentException.class, () -> AuditRules.canAppendEvent(trail, null));
        assertThrows(IllegalArgumentException.class, () -> AuditRules.requiresReview((AuditEvent) null));
        assertThrows(IllegalArgumentException.class, () -> AuditRules.requiresReview((AuditTrail) null));
        assertThrows(IllegalArgumentException.class, () -> AuditRules.containsSecuritySensitiveEvents(null));
        assertThrows(IllegalArgumentException.class, () -> AuditRules.containsFinancialImpactEvents(null));
        assertThrows(IllegalArgumentException.class, () -> AuditRules.isChronological(null));
    }

    private static AuditEvent event(String eventId, AuditActionType actionType, String occurredAt) {
        return AuditEvent.userAction(
                eventId,
                "SHIPMENT",
                "SHP-001",
                "USER-001",
                actionType,
                Instant.parse(occurredAt),
                Notes.empty()
        );
    }
}
