package it.gabriele.truckflow.domain.driverscheduling;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/** Regole HR operative su turni, disponibilità, assenze e disciplina. */
public final class DriverSchedulingRules {

  private DriverSchedulingRules() {}

  public static boolean hasShiftConflict(DriverShift newShift, List<DriverShift> existingShifts) {
    if (newShift == null) {
      throw new IllegalArgumentException("Il nuovo turno è obbligatorio.");
    }
    List<DriverShift> safeExisting = existingShifts == null ? List.of() : existingShifts;
    return safeExisting.stream().anyMatch(newShift::overlaps);
  }

  public static boolean isAvailableForMission(
      String driverCode,
      LocalDateTime missionStart,
      List<DriverAbsence> absences,
      List<DriverDisciplinaryRecord> records) {
    String normalizedDriver = normalize(driverCode, "Il codice autista è obbligatorio.");
    if (missionStart == null) {
      throw new IllegalArgumentException("La data missione è obbligatoria.");
    }
    LocalDate missionDate = missionStart.toLocalDate();
    boolean absent =
        (absences == null ? List.<DriverAbsence>of() : absences)
            .stream()
                .anyMatch(
                    absence ->
                        absence.driverCode().equals(normalizedDriver)
                            && absence.covers(missionDate));
    boolean blocked =
        (records == null ? List.<DriverDisciplinaryRecord>of() : records)
            .stream()
                .anyMatch(
                    record ->
                        record.driverCode().equals(normalizedDriver) && record.blocksAssignment());
    return !absent && !blocked;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
