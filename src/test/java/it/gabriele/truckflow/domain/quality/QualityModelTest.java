package it.gabriele.truckflow.domain.quality;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/** Testa qualità servizio, non conformità e azioni correttive. */
class QualityModelTest {

  @Test
  void shouldOpenQualityEvent() {
    QualityEvent event = qualityEvent();

    assertEquals("QLT-001", event.getEventCode());
    assertEquals(QualityEventType.CUSTOMER_COMPLAINT, event.getType());
    assertTrue(event.isActive());
    assertTrue(event.requiresManagementReview());
  }

  @Test
  void shouldAssignCorrectiveActionAndCloseCriticalEvent() {
    CorrectiveAction action =
        CorrectiveAction.open(
                "act-001",
                "USR-OPS-001",
                "Contattare cliente e analizzare causa",
                LocalDate.of(2026, 6, 5),
                Notes.empty())
            .complete();

    QualityEvent closed =
        qualityEvent().assignCorrectiveAction(action).close(Instant.parse("2026-06-02T10:00:00Z"));

    assertEquals(QualityStatus.CLOSED, closed.getStatus());
    assertFalse(closed.isActive());
  }

  @Test
  void shouldNotCloseCriticalEventWithoutCorrectiveAction() {
    assertThrows(
        IllegalStateException.class,
        () -> qualityEvent().close(Instant.parse("2026-06-02T10:00:00Z")));
  }

  private static QualityEvent qualityEvent() {
    return QualityEvent.open(
        "qlt-001",
        QualityEventType.CUSTOMER_COMPLAINT,
        QualitySeverity.CRITICAL,
        "shp-001",
        "Reclamo cliente per merce danneggiata",
        Instant.parse("2026-06-01T10:00:00Z"),
        Notes.of("Cliente segnala colli danneggiati al POD."));
  }
}
