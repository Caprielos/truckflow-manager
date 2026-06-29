package it.gabriele.truckflow.application.common;

/** Errore applicativo esposto dai casi d'uso senza dipendere da web, database o framework. */
public record ApplicationError(String code, String message) {

  public ApplicationError {
    code = normalize(code, "Il codice errore è obbligatorio.");
    message = normalize(message, "Il messaggio errore è obbligatorio.");
  }

  public static ApplicationError of(String code, String message) {
    return new ApplicationError(code, message);
  }

  private static String normalize(String value, String errorMessage) {
    if (value == null) {
      throw new IllegalArgumentException(errorMessage);
    }
    String normalized = value.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException(errorMessage);
    }
    return normalized;
  }
}
