package it.gabriele.truckflow.domain.audit;

import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa AuditEvent.
 */
class AuditEventTest {

    @Test
    void shouldCreateAuditEvent() {
        AuditEvent event = standardEvent();

        assertEquals("AUD-001", event.getEventId());
        assertEquals("SHIPMENT", event.getAggregateType());
        assertEquals("SHP-001", event.getAggregateId());
        assertEquals(AuditActorType.USER, event.getActorType());
        assertEquals("USER-001", event.getActorId());
        assertEquals(AuditActionType.CREATED, event.getActionType());
        assertEquals(AuditSeverity.INFO, event.getSeverity());
        assertEquals(Instant.parse("2026-07-01T08:00:00Z"), event.getOccurredAt());
        assertTrue(event.isHumanAction());
        assertTrue(event.isDataChange());
        assertFalse(event.requiresReview());
    }

    @Test
    void shouldCreateSystemAction() {
        AuditEvent event = AuditEvent.systemAction(
                "AUD-002",
                "SHIPMENT",
                "SHP-001",
                AuditActionType.STATUS_CHANGED,
                Instant.parse("2026-07-01T09:00:00Z"),
                Notes.empty()
        );

        assertTrue(event.isSystemAction());
        assertEquals("SYSTEM", event.getActorId());
    }

    @Test
    void shouldCreateIntegrationAction() {
        AuditEvent event = AuditEvent.integrationAction(
                "AUD-003",
                "QUOTE",
                "QUOTE-001",
                "VIAMICHELIN",
                AuditActionType.EXTERNAL_ESTIMATE_IMPORTED,
                Instant.parse("2026-07-01T10:00:00Z"),
                Notes.empty()
        );

        assertTrue(event.isIntegrationAction());
        assertEquals("VIAMICHELIN", event.getActorId());
    }

    @Test
    void shouldNormalizeCodes() {
        AuditEvent event = AuditEvent.userAction(
                "  aud_001  ",
                "  shipment  ",
                "  shp_001  ",
                "  user_001  ",
                AuditActionType.CREATED,
                Instant.parse("2026-07-01T08:00:00Z"),
                Notes.empty()
        );

        assertEquals("AUD_001", event.getEventId());
        assertEquals("SHIPMENT", event.getAggregateType());
        assertEquals("SHP_001", event.getAggregateId());
        assertEquals("USER_001", event.getActorId());
    }

    @Test
    void shouldRejectInvalidCodes() {
        assertThrows(IllegalArgumentException.class, () -> AuditEvent.userAction(
                null,
                "SHIPMENT",
                "SHP-001",
                "USER-001",
                AuditActionType.CREATED,
                Instant.parse("2026-07-01T08:00:00Z"),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> AuditEvent.userAction(
                "AUD 001",
                "SHIPMENT",
                "SHP-001",
                "USER-001",
                AuditActionType.CREATED,
                Instant.parse("2026-07-01T08:00:00Z"),
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> AuditEvent.of(
                "AUD-001",
                "SHIPMENT",
                "SHP-001",
                null,
                "USER-001",
                AuditActionType.CREATED,
                AuditSeverity.INFO,
                Instant.parse("2026-07-01T08:00:00Z"),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> AuditEvent.of(
                "AUD-001",
                "SHIPMENT",
                "SHP-001",
                AuditActorType.USER,
                "USER-001",
                null,
                AuditSeverity.INFO,
                Instant.parse("2026-07-01T08:00:00Z"),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> AuditEvent.of(
                "AUD-001",
                "SHIPMENT",
                "SHP-001",
                AuditActorType.USER,
                "USER-001",
                AuditActionType.CREATED,
                null,
                Instant.parse("2026-07-01T08:00:00Z"),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> AuditEvent.of(
                "AUD-001",
                "SHIPMENT",
                "SHP-001",
                AuditActorType.USER,
                "USER-001",
                AuditActionType.CREATED,
                AuditSeverity.INFO,
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> AuditEvent.of(
                "AUD-001",
                "SHIPMENT",
                "SHP-001",
                AuditActorType.USER,
                "USER-001",
                AuditActionType.CREATED,
                AuditSeverity.INFO,
                Instant.parse("2026-07-01T08:00:00Z"),
                null
        ));
    }

    @Test
    void shouldDetectSecurityAndFinancialEvents() {
        AuditEvent loginFailed = AuditEvent.of(
                "AUD-004",
                "USER_ACCOUNT",
                "USER-001",
                AuditActorType.USER,
                "USER-001",
                AuditActionType.LOGIN_FAILED,
                AuditSeverity.WARNING,
                Instant.parse("2026-07-01T08:00:00Z"),
                Notes.empty()
        );

        AuditEvent payment = AuditEvent.userAction(
                "AUD-005",
                "INVOICE",
                "INV-001",
                "USER-001",
                AuditActionType.PAYMENT_REGISTERED,
                Instant.parse("2026-07-01T09:00:00Z"),
                Notes.empty()
        );

        assertTrue(loginFailed.isSecuritySensitive());
        assertTrue(loginFailed.requiresReview());

        assertTrue(payment.hasFinancialImpact());
        assertTrue(payment.isDataChange());
    }

    @Test
    void shouldCheckAggregateAndTimeOrdering() {
        AuditEvent first = standardEvent();

        AuditEvent second = AuditEvent.userAction(
                "AUD-002",
                "SHIPMENT",
                "SHP-001",
                "USER-001",
                AuditActionType.STATUS_CHANGED,
                Instant.parse("2026-07-01T09:00:00Z"),
                Notes.empty()
        );

        assertTrue(first.isForAggregate("shipment", "shp-001"));
        assertTrue(first.isSameAggregate(second));
        assertTrue(first.isBeforeOrAtSameTime(second));
    }

    @Test
    void shouldDetectNotes() {
        AuditEvent event = AuditEvent.userAction(
                "AUD-001",
                "SHIPMENT",
                "SHP-001",
                "USER-001",
                AuditActionType.CREATED,
                Instant.parse("2026-07-01T08:00:00Z"),
                Notes.of("Spedizione creata da ordine accettato")
        );

        assertTrue(event.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        assertEquals(
                "AUD-001 - SHIPMENT:SHP-001 - CREATED - USER:USER-001",
                standardEvent().formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentEventsEqual() {
        AuditEvent first = standardEvent();
        AuditEvent second = standardEvent();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldExposeEnumDetails() {
        assertTrue(AuditActorType.USER.isHumanActor());
        assertFalse(AuditActorType.SYSTEM.isHumanActor());

        assertTrue(AuditSeverity.WARNING.requiresReview());
        assertTrue(AuditSeverity.CRITICAL.isAtLeast(AuditSeverity.ERROR));

        assertTrue(AuditActionType.CREATED.isDataChange());
        assertTrue(AuditActionType.LOGIN_FAILED.isSecuritySensitive());
        assertTrue(AuditActionType.PAYMENT_REGISTERED.hasFinancialImpact());
    }

    private static AuditEvent standardEvent() {
        return AuditEvent.userAction(
                "AUD-001",
                "SHIPMENT",
                "SHP-001",
                "USER-001",
                AuditActionType.CREATED,
                Instant.parse("2026-07-01T08:00:00Z"),
                Notes.empty()
        );
    }
}
