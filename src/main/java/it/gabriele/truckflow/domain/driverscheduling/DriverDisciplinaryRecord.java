package it.gabriele.truckflow.domain.driverscheduling;

import java.time.LocalDate;

/** Storico disciplinare o sicurezza autista. */
public record DriverDisciplinaryRecord(
    String recordCode,
    String driverCode,
    LocalDate eventDate,
    String reason,
    int severityScore,
    boolean closed) {

  public DriverDisciplinaryRecord {
    recordCode = normalize(recordCode, "Il codice record è obbligatorio.");
    driverCode = normalize(driverCode, "Il codice autista è obbligatorio.");
    if (eventDate == null) {
      throw new IllegalArgumentException("La data evento è obbligatoria.");
    }
    reason = normalize(reason, "Il motivo è obbligatorio.");
    if (severityScore < 1 || severityScore > 10) {
      throw new IllegalArgumentException("La severità deve essere tra 1 e 10.");
    }
  }

  public boolean blocksAssignment() {
    return !closed && severityScore >= 8;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
