package it.gabriele.truckflow.domain.inventory;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.math.BigDecimal;
import java.util.Objects;

/** Articolo di magazzino: ricambio, gomma, olio, AdBlue, attrezzatura ADR, DPI, pallet, ecc. */
public final class InventoryItem {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_DESCRIPTION_LENGTH = 200;
  private static final int MAX_UNIT_LENGTH = 20;

  private final String itemCode;
  private final InventoryItemType type;
  private final String description;
  private final String unitOfMeasure;
  private final Money unitCost;
  private final double minimumStockQuantity;
  private final Notes notes;

  private InventoryItem(
      String itemCode,
      InventoryItemType type,
      String description,
      String unitOfMeasure,
      Money unitCost,
      double minimumStockQuantity,
      Notes notes) {
    this.itemCode = validateCode(itemCode, "Il codice articolo è obbligatorio.");
    if (type == null) {
      throw new IllegalArgumentException("Il tipo articolo è obbligatorio.");
    }
    this.description =
        validateText(
            description, "La descrizione articolo è obbligatoria.", MAX_DESCRIPTION_LENGTH);
    this.unitOfMeasure =
        validateText(unitOfMeasure, "L'unità di misura articolo è obbligatoria.", MAX_UNIT_LENGTH)
            .toUpperCase();
    if (unitCost == null) {
      throw new IllegalArgumentException("Il costo unitario articolo è obbligatorio.");
    }
    if (minimumStockQuantity < 0
        || Double.isNaN(minimumStockQuantity)
        || Double.isInfinite(minimumStockQuantity)) {
      throw new IllegalArgumentException(
          "La scorta minima deve essere un numero valido non negativo.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note articolo sono obbligatorie.");
    }
    this.type = type;
    this.unitCost = unitCost;
    this.minimumStockQuantity = minimumStockQuantity;
    this.notes = notes;
  }

  public static InventoryItem of(
      String itemCode,
      InventoryItemType type,
      String description,
      String unitOfMeasure,
      Money unitCost,
      double minimumStockQuantity,
      Notes notes) {
    return new InventoryItem(
        itemCode, type, description, unitOfMeasure, unitCost, minimumStockQuantity, notes);
  }

  private static String validateCode(String code, String message) {
    if (code == null) {
      throw new IllegalArgumentException(message);
    }
    String normalized = code.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    if (normalized.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }
    return normalized;
  }

  private static String validateText(String value, String message, int maxLength) {
    if (value == null) {
      throw new IllegalArgumentException(message);
    }
    String normalized = value.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    if (normalized.length() > maxLength) {
      throw new IllegalArgumentException("Il testo non può superare " + maxLength + " caratteri.");
    }
    return normalized;
  }

  public String getItemCode() {
    return itemCode;
  }

  public InventoryItemType getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public String getUnitOfMeasure() {
    return unitOfMeasure;
  }

  public Money getUnitCost() {
    return unitCost;
  }

  public double getMinimumStockQuantity() {
    return minimumStockQuantity;
  }

  public Notes getNotes() {
    return notes;
  }

  public Money calculateStockValue(double quantity) {
    if (quantity < 0 || Double.isNaN(quantity) || Double.isInfinite(quantity)) {
      throw new IllegalArgumentException(
          "La quantità da valorizzare deve essere valida e non negativa.");
    }
    return Money.of(
        unitCost.getAmount().multiply(BigDecimal.valueOf(quantity)), unitCost.getCurrency());
  }

  public boolean isBelowMinimumStock(double availableQuantity) {
    if (availableQuantity < 0
        || Double.isNaN(availableQuantity)
        || Double.isInfinite(availableQuantity)) {
      throw new IllegalArgumentException(
          "La quantità disponibile deve essere valida e non negativa.");
    }
    return availableQuantity < minimumStockQuantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InventoryItem that)) return false;
    return Double.compare(minimumStockQuantity, that.minimumStockQuantity) == 0
        && itemCode.equals(that.itemCode)
        && type == that.type
        && description.equals(that.description)
        && unitOfMeasure.equals(that.unitOfMeasure)
        && unitCost.equals(that.unitCost)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        itemCode, type, description, unitOfMeasure, unitCost, minimumStockQuantity, notes);
  }
}
