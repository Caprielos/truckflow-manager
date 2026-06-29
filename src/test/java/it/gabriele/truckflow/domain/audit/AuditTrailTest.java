package it.gabriele.truckflow.domain.audit;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Testa AuditTrail. */
class AuditTrailTest {

  @Test
  void shouldCreateAuditTrailAndOrderEventsByTime() {
    AuditEvent second = event("AUD-002", AuditActionType.STATUS_CHANGED, "2026-07-01T10:00:00Z");
    AuditEvent first = event("AUD-001", AuditActionType.CREATED, "2026-07-01T08:00:00Z");

    AuditTrail trail = AuditTrail.of(List.of(second, first));

    assertEquals(2, trail.getEventCount());
    assertEquals("AUD-001", trail.getFirstEvent().getEventId());
    assertEquals("AUD-002", trail.getLatestEvent().getEventId());
    assertEquals("SHIPMENT", trail.getAggregateType());
    assertEquals("SHP-001", trail.getAggregateId());
  }

  @Test
  void shouldNotAllowEmptyNullOrNullItems() {
    assertThrows(IllegalArgumentException.class, () -> AuditTrail.of((List<AuditEvent>) null));
    assertThrows(IllegalArgumentException.class, () -> AuditTrail.of(List.of()));

    List<AuditEvent> eventsWithNull =
        Arrays.asList(event("AUD-001", AuditActionType.CREATED, "2026-07-01T08:00:00Z"), null);

    assertThrows(IllegalArgumentException.class, () -> AuditTrail.of(eventsWithNull));
  }

  @Test
  void shouldRejectEventsFromDifferentAggregates() {
    AuditEvent first = event("AUD-001", AuditActionType.CREATED, "2026-07-01T08:00:00Z");

    AuditEvent differentAggregate =
        AuditEvent.userAction(
            "AUD-002",
            "INVOICE",
            "INV-001",
            "USER-001",
            AuditActionType.CREATED,
            Instant.parse("2026-07-01T09:00:00Z"),
            Notes.empty());

    assertThrows(IllegalArgumentException.class, () -> AuditTrail.of(first, differentAggregate));
  }

  @Test
  void shouldRejectDuplicatedEventIds() {
    AuditEvent first = event("AUD-001", AuditActionType.CREATED, "2026-07-01T08:00:00Z");
    AuditEvent duplicated = event("AUD-001", AuditActionType.UPDATED, "2026-07-01T09:00:00Z");

    assertThrows(IllegalArgumentException.class, () -> AuditTrail.of(first, duplicated));
  }

  @Test
  void shouldDetectActionTypesAndEventKinds() {
    AuditTrail trail =
        AuditTrail.of(
            event("AUD-001", AuditActionType.CREATED, "2026-07-01T08:00:00Z"),
            event("AUD-002", AuditActionType.PAYMENT_REGISTERED, "2026-07-01T09:00:00Z"),
            AuditEvent.of(
                "AUD-003",
                "SHIPMENT",
                "SHP-001",
                AuditActorType.USER,
                "USER-001",
                AuditActionType.PERMISSION_DENIED,
                AuditSeverity.WARNING,
                Instant.parse("2026-07-01T10:00:00Z"),
                Notes.empty()));

    assertTrue(trail.hasActionType(AuditActionType.CREATED));
    assertTrue(trail.hasFinancialImpactEvents());
    assertTrue(trail.hasSecuritySensitiveEvents());
    assertTrue(trail.hasReviewRequiredEvents());
  }

  @Test
  void shouldFilterEventsByActionType() {
    AuditTrail trail =
        AuditTrail.of(
            event("AUD-001", AuditActionType.UPDATED, "2026-07-01T08:00:00Z"),
            event("AUD-002", AuditActionType.UPDATED, "2026-07-01T09:00:00Z"),
            event("AUD-003", AuditActionType.STATUS_CHANGED, "2026-07-01T10:00:00Z"));

    assertEquals(2, trail.getEventsByActionType(AuditActionType.UPDATED).size());
  }

  @Test
  void shouldCheckContainsEventId() {
    AuditTrail trail = standardTrail();

    assertTrue(trail.containsEventId("aud-001"));
    assertFalse(trail.containsEventId("AUD-999"));
    assertThrows(IllegalArgumentException.class, () -> trail.containsEventId(null));
  }

  @Test
  void shouldNotAllowNullTypeFiltering() {
    AuditTrail trail = standardTrail();

    assertThrows(IllegalArgumentException.class, () -> trail.hasActionType(null));
    assertThrows(IllegalArgumentException.class, () -> trail.getEventsByActionType(null));
  }

  @Test
  void shouldFormatSingleLine() {
    assertEquals(
        "SHIPMENT:SHP-001 - events: 2 - latest: STATUS_CHANGED",
        standardTrail().formatSingleLine());
  }

  @Test
  void shouldConsiderEquivalentTrailsEqual() {
    AuditTrail first = standardTrail();
    AuditTrail second = standardTrail();

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }

  private static AuditTrail standardTrail() {
    return AuditTrail.of(
        event("AUD-001", AuditActionType.CREATED, "2026-07-01T08:00:00Z"),
        event("AUD-002", AuditActionType.STATUS_CHANGED, "2026-07-01T09:00:00Z"));
  }

  private static AuditEvent event(String eventId, AuditActionType actionType, String occurredAt) {
    return AuditEvent.userAction(
        eventId,
        "SHIPMENT",
        "SHP-001",
        "USER-001",
        actionType,
        Instant.parse(occurredAt),
        Notes.empty());
  }
}
