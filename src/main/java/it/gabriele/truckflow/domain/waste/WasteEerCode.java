package it.gabriele.truckflow.domain.waste;

/** Codice EER/CER del rifiuto. */
public record WasteEerCode(String code, String description, boolean hazardous) {

  public WasteEerCode {
    code = normalize(code, "Il codice EER/CER è obbligatorio.");
    if (!code.matches("\\d{2}\\.\\d{2}\\.\\d{2}\\*?")) {
      throw new IllegalArgumentException("Il codice EER/CER deve avere formato 00.00.00.");
    }
    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("La descrizione rifiuto è obbligatoria.");
    }
    description = description.trim();
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
