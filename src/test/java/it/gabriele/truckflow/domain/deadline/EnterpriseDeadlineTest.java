package it.gabriele.truckflow.domain.deadline;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/** Testa scadenze enterprise e regole di attenzione. */
class EnterpriseDeadlineTest {

  @Test
  void shouldCreatePlannedDriverDeadline() {
    EnterpriseDeadline deadline = driverAdrDeadline();

    assertEquals("DLN-001", deadline.getDeadlineCode());
    assertEquals(DeadlineOwnerType.DRIVER, deadline.getOwnerType());
    assertEquals("DRV-001", deadline.getOwnerCode());
    assertEquals(DeadlineType.DRIVER_ADR, deadline.getType());
    assertEquals(DeadlineStatus.PLANNED, deadline.getStatus());
    assertTrue(deadline.blocksOperationsWhenExpired());
  }

  @Test
  void shouldDetectDueSoonAndOverdue() {
    EnterpriseDeadline deadline = driverAdrDeadline();

    assertFalse(deadline.isDueSoon(LocalDate.of(2026, 4, 30)));
    assertTrue(deadline.isDueSoon(LocalDate.of(2026, 5, 15)));
    assertTrue(deadline.isOverdue(LocalDate.of(2026, 6, 2)));
  }

  @Test
  void shouldRefreshStatusFromReferenceDate() {
    EnterpriseDeadline dueSoon = driverAdrDeadline().refreshStatus(LocalDate.of(2026, 5, 15));
    EnterpriseDeadline overdue = driverAdrDeadline().refreshStatus(LocalDate.of(2026, 6, 2));

    assertEquals(DeadlineStatus.DUE_SOON, dueSoon.getStatus());
    assertEquals(DeadlineStatus.OVERDUE, overdue.getStatus());
    assertTrue(DeadlineRules.blocksOperations(overdue, LocalDate.of(2026, 6, 2)));
  }

  @Test
  void shouldCompleteOpenDeadline() {
    EnterpriseDeadline completed = driverAdrDeadline().complete();

    assertEquals(DeadlineStatus.COMPLETED, completed.getStatus());
    assertFalse(completed.getStatus().requiresAttention());
  }

  @Test
  void shouldRejectWarningDateAfterDueDate() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            EnterpriseDeadline.planned(
                "DLN-001",
                DeadlineOwnerType.DRIVER,
                "DRV-001",
                DeadlineType.DRIVER_ADR,
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 2),
                DeadlineSeverity.CRITICAL,
                Notes.empty()));
  }

  private static EnterpriseDeadline driverAdrDeadline() {
    return EnterpriseDeadline.planned(
        "dln-001",
        DeadlineOwnerType.DRIVER,
        "drv-001",
        DeadlineType.DRIVER_ADR,
        LocalDate.of(2026, 6, 1),
        LocalDate.of(2026, 5, 1),
        DeadlineSeverity.CRITICAL,
        Notes.of("ADR autista in scadenza"));
  }
}
