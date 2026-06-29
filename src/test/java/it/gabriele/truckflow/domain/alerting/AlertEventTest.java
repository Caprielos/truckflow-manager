package it.gabriele.truckflow.domain.alerting;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import org.junit.jupiter.api.Test;

/** Testa alert enterprise e ciclo di vita. */
class AlertEventTest {

  @Test
  void shouldCreateCriticalAlert() {
    AlertEvent alert = criticalAlert();

    assertEquals("ALT-001", alert.getAlertCode());
    assertEquals(AlertType.DEADLINE_OVERDUE, alert.getType());
    assertEquals(AlertSeverity.CRITICAL, alert.getSeverity());
    assertTrue(alert.isActive());
    assertTrue(AlertRules.requiresImmediateAttention(alert));
    assertTrue(AlertRules.requiresEscalation(alert));
  }

  @Test
  void shouldAcknowledgeAndResolveAlert() {
    AlertEvent acknowledged = criticalAlert().acknowledge(Instant.parse("2026-06-01T09:10:00Z"));
    AlertEvent resolved =
        acknowledged.resolve(
            Instant.parse("2026-06-01T10:00:00Z"), Notes.of("Scadenza aggiornata"));

    assertEquals(AlertStatus.ACKNOWLEDGED, acknowledged.getStatus());
    assertEquals(AlertStatus.RESOLVED, resolved.getStatus());
    assertFalse(resolved.isActive());
  }

  @Test
  void shouldRejectResolveWithoutNotes() {
    assertThrows(
        IllegalArgumentException.class,
        () -> criticalAlert().resolve(Instant.parse("2026-06-01T10:00:00Z"), Notes.empty()));
  }

  private static AlertEvent criticalAlert() {
    return AlertEvent.open(
        "alt-001",
        AlertType.DEADLINE_OVERDUE,
        AlertSeverity.CRITICAL,
        AlertSourceType.DEADLINE,
        "dln-001",
        "ADR autista scaduto",
        "Il certificato ADR dell'autista DRV-001 è scaduto.",
        Instant.parse("2026-06-01T09:00:00Z"),
        Notes.empty());
  }
}
