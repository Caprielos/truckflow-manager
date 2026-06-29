package it.gabriele.truckflow.domain.foodsafety;

import java.time.Instant;

/** Registro pulizia/sanificazione del mezzo o vano carico. */
public record SanitationRecord(
    String vehicleCode,
    Instant sanitizedAt,
    Instant validUntil,
    String method,
    boolean sealedAfterSanitation,
    boolean contaminationFound) {

  public SanitationRecord {
    vehicleCode = normalize(vehicleCode, "Il codice veicolo è obbligatorio.");
    if (sanitizedAt == null) {
      throw new IllegalArgumentException("La data sanificazione è obbligatoria.");
    }
    if (validUntil == null) {
      throw new IllegalArgumentException("La validità sanificazione è obbligatoria.");
    }
    if (validUntil.isBefore(sanitizedAt)) {
      throw new IllegalArgumentException(
          "La validità sanificazione non può precedere l'intervento.");
    }
    if (method == null || method.trim().isEmpty()) {
      throw new IllegalArgumentException("Il metodo di sanificazione è obbligatorio.");
    }
    method = method.trim();
  }

  public boolean isValidAt(Instant instant) {
    if (instant == null) {
      throw new IllegalArgumentException("L'istante controllo sanificazione è obbligatorio.");
    }
    return !instant.isAfter(validUntil) && !contaminationFound;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
