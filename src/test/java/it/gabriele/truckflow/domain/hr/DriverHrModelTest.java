package it.gabriele.truckflow.domain.hr;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/** Testa HR autisti enterprise: formazione e visite mediche. */
class DriverHrModelTest {

  @Test
  void shouldCreateCompletedTraining() {
    DriverTrainingRecord training =
        DriverTrainingRecord.completed(
            "trn-001",
            "drv-001",
            DriverTrainingType.ADR,
            "Aggiornamento ADR",
            LocalDate.of(2026, 1, 10),
            LocalDate.of(2027, 1, 10),
            Notes.empty());

    assertEquals("TRN-001", training.getTrainingCode());
    assertEquals(DriverTrainingStatus.COMPLETED, training.getStatus());
    assertFalse(DriverHrRules.trainingIsExpired(training, LocalDate.of(2026, 6, 1)));
  }

  @Test
  void shouldDetectExpiredTraining() {
    DriverTrainingRecord training =
        DriverTrainingRecord.completed(
            "trn-001",
            "drv-001",
            DriverTrainingType.HACCP_FOOD,
            "HACCP trasporto alimentare",
            LocalDate.of(2025, 1, 10),
            LocalDate.of(2026, 1, 10),
            Notes.empty());

    assertTrue(DriverHrRules.trainingIsExpired(training, LocalDate.of(2026, 6, 1)));
  }

  @Test
  void shouldCreateMedicalCheckAndBlockNotFitDriver() {
    DriverMedicalCheck fit =
        DriverMedicalCheck.fit(
            "med-001",
            "drv-001",
            LocalDate.of(2026, 1, 10),
            LocalDate.of(2027, 1, 10),
            Notes.empty());
    DriverMedicalCheck notFit =
        DriverMedicalCheck.notFit("med-002", "drv-002", LocalDate.of(2026, 1, 10), Notes.empty());

    assertTrue(DriverHrRules.canBeAssignedToMission(fit));
    assertFalse(DriverHrRules.canBeAssignedToMission(notFit));
  }
}
