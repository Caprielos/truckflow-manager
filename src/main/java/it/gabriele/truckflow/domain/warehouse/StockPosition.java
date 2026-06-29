package it.gabriele.truckflow.domain.warehouse;

import java.time.LocalDateTime;

/** Giacenza fisica di un item in una ubicazione. */
public record StockPosition(
    String itemCode,
    String batchCode,
    String locationCode,
    int quantity,
    LocalDateTime lastMovementAt,
    boolean quarantined) {

  public StockPosition {
    itemCode = normalize(itemCode, "Il codice articolo è obbligatorio.");
    batchCode = batchCode == null ? "" : batchCode.trim().toUpperCase();
    locationCode = normalize(locationCode, "Il codice ubicazione è obbligatorio.");
    if (quantity < 0) {
      throw new IllegalArgumentException("La quantità non può essere negativa.");
    }
    if (lastMovementAt == null) {
      throw new IllegalArgumentException("La data ultimo movimento è obbligatoria.");
    }
  }

  public boolean isAvailable() {
    return quantity > 0 && !quarantined;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
