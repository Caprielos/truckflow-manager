package it.gabriele.truckflow.domain.tachograph;

import java.time.Instant;
import java.time.LocalDate;

/** Scarico dati carta conducente/tachigrafo. */
public record DriverCardDownload(
    String downloadCode,
    String driverCode,
    Instant downloadedAt,
    LocalDate periodStart,
    LocalDate periodEnd,
    boolean signedArchive,
    boolean parseSuccessful) {

  public DriverCardDownload {
    downloadCode = normalize(downloadCode, "Il codice scarico tachigrafo è obbligatorio.");
    driverCode = normalize(driverCode, "Il codice autista è obbligatorio.");
    if (downloadedAt == null) {
      throw new IllegalArgumentException("La data scarico tachigrafo è obbligatoria.");
    }
    if (periodStart == null || periodEnd == null) {
      throw new IllegalArgumentException("Il periodo scarico tachigrafo è obbligatorio.");
    }
    if (periodEnd.isBefore(periodStart)) {
      throw new IllegalArgumentException("La fine periodo non può precedere l'inizio.");
    }
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
