package it.gabriele.truckflow.domain.driverscheduling;

import java.time.Duration;
import java.time.LocalDateTime;

/** Turno pianificato o consuntivato di un autista. */
public record DriverShift(
    String shiftCode,
    String driverCode,
    LocalDateTime startAt,
    LocalDateTime endAt,
    DriverDutyStatus dutyStatus,
    boolean onCall) {

  public DriverShift {
    shiftCode = normalize(shiftCode, "Il codice turno è obbligatorio.");
    driverCode = normalize(driverCode, "Il codice autista è obbligatorio.");
    if (startAt == null || endAt == null || !endAt.isAfter(startAt)) {
      throw new IllegalArgumentException("La finestra turno non è valida.");
    }
    if (dutyStatus == null) {
      throw new IllegalArgumentException("Lo stato turno è obbligatorio.");
    }
  }

  public Duration duration() {
    return Duration.between(startAt, endAt);
  }

  public boolean overlaps(DriverShift other) {
    if (other == null) {
      throw new IllegalArgumentException("Il turno da confrontare è obbligatorio.");
    }
    return driverCode.equals(other.driverCode)
        && startAt.isBefore(other.endAt)
        && other.startAt.isBefore(endAt);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
