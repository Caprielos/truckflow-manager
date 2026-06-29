package it.gabriele.truckflow.domain.tachograph;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/** Intervallo attività tachigrafo. */
public record TachographActivity(
    String driverCode, TachographActivityType activityType, Instant startAt, Instant endAt) {

  public TachographActivity {
    driverCode = normalize(driverCode, "Il codice autista è obbligatorio.");
    Objects.requireNonNull(activityType, "Il tipo attività tachigrafo è obbligatorio.");
    Objects.requireNonNull(startAt, "L'inizio attività tachigrafo è obbligatorio.");
    Objects.requireNonNull(endAt, "La fine attività tachigrafo è obbligatoria.");
    if (!endAt.isAfter(startAt)) {
      throw new IllegalArgumentException("La fine attività deve essere successiva all'inizio.");
    }
  }

  public long durationMinutes() {
    return Duration.between(startAt, endAt).toMinutes();
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
