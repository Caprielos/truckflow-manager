package it.gabriele.truckflow.domain.tachograph;

import java.time.Instant;
import java.util.Objects;

/** Violazione tachigrafo/ore guida da trasformare in alert e audit. */
public record DrivingTimeViolation(
    String violationCode,
    String driverCode,
    DrivingTimeViolationType type,
    Instant occurredAt,
    int minutesOverLimit,
    boolean acknowledged) {

  public DrivingTimeViolation {
    violationCode = normalize(violationCode, "Il codice violazione tachigrafo è obbligatorio.");
    driverCode = normalize(driverCode, "Il codice autista è obbligatorio.");
    Objects.requireNonNull(type, "Il tipo violazione tachigrafo è obbligatorio.");
    Objects.requireNonNull(occurredAt, "La data violazione tachigrafo è obbligatoria.");
    if (minutesOverLimit < 0) {
      throw new IllegalArgumentException("I minuti oltre limite non possono essere negativi.");
    }
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
