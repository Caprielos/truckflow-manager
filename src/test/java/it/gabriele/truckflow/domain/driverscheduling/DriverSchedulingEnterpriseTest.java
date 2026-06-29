package it.gabriele.truckflow.domain.driverscheduling;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class DriverSchedulingEnterpriseTest {

  @Test
  void shouldDetectAbsenceDisciplinaryBlockAndShiftConflicts() {
    DriverShift shift =
        new DriverShift(
            "shift-001",
            "drv-001",
            LocalDateTime.of(2026, 6, 29, 8, 0),
            LocalDateTime.of(2026, 6, 29, 16, 0),
            DriverDutyStatus.DRIVING,
            false);
    DriverShift overlapping =
        new DriverShift(
            "shift-002",
            "drv-001",
            LocalDateTime.of(2026, 6, 29, 12, 0),
            LocalDateTime.of(2026, 6, 29, 18, 0),
            DriverDutyStatus.OTHER_WORK,
            false);
    DriverAbsence absence =
        new DriverAbsence(
            "abs-001",
            "drv-001",
            DriverAbsenceType.SICKNESS,
            LocalDate.of(2026, 6, 30),
            LocalDate.of(2026, 7, 2),
            true);
    DriverDisciplinaryRecord record =
        new DriverDisciplinaryRecord(
            "rec-001", "drv-002", LocalDate.of(2026, 6, 20), "grave safety issue", 9, false);

    assertTrue(DriverSchedulingRules.hasShiftConflict(overlapping, List.of(shift)));
    assertFalse(
        DriverSchedulingRules.isAvailableForMission(
            "drv-001", LocalDateTime.of(2026, 6, 30, 8, 0), List.of(absence), List.of()));
    assertFalse(
        DriverSchedulingRules.isAvailableForMission(
            "drv-002", LocalDateTime.of(2026, 6, 30, 8, 0), List.of(), List.of(record)));
  }
}
