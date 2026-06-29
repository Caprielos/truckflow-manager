package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Bene aziendale acquistato: camion, trattore, rimorchio, allestimento, frigo, attrezzatura, gomme,
 * telematica. Conserva il costo storico e permette di allocare l'ammortamento nei periodi o nelle
 * missioni.
 */
public final class FleetAssetPurchase {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_DESCRIPTION_LENGTH = 200;

  private final String assetCode;
  private final FleetAssetCategory category;
  private final String supplierInvoiceNumber;
  private final String assignedVehicleFleetNumber;
  private final String description;
  private final LocalDate purchaseDate;
  private final Money purchasePrice;
  private final Money residualValue;
  private final int usefulLifeMonths;
  private final Notes notes;

  private FleetAssetPurchase(
      String assetCode,
      FleetAssetCategory category,
      String supplierInvoiceNumber,
      String assignedVehicleFleetNumber,
      String description,
      LocalDate purchaseDate,
      Money purchasePrice,
      Money residualValue,
      int usefulLifeMonths,
      Notes notes) {
    this.assetCode = validateCode(assetCode, "Il codice bene aziendale è obbligatorio.");
    if (category == null) {
      throw new IllegalArgumentException("La categoria bene aziendale è obbligatoria.");
    }
    this.supplierInvoiceNumber =
        validateCode(supplierInvoiceNumber, "Il numero fattura fornitore è obbligatorio.");
    this.assignedVehicleFleetNumber = normalizeOptionalCode(assignedVehicleFleetNumber);
    this.description = validateDescription(description);
    if (purchaseDate == null) {
      throw new IllegalArgumentException("La data acquisto bene è obbligatoria.");
    }
    if (purchasePrice == null) {
      throw new IllegalArgumentException("Il prezzo acquisto bene è obbligatorio.");
    }
    if (purchasePrice.getAmount().signum() == 0) {
      throw new IllegalArgumentException("Il prezzo acquisto bene deve essere maggiore di zero.");
    }
    if (residualValue == null) {
      throw new IllegalArgumentException("Il valore residuo bene è obbligatorio.");
    }
    purchasePrice.add(residualValue);
    if (residualValue.isGreaterThan(purchasePrice)) {
      throw new IllegalArgumentException(
          "Il valore residuo non può superare il prezzo di acquisto.");
    }
    if (usefulLifeMonths <= 0) {
      throw new IllegalArgumentException("La vita utile deve essere positiva.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note bene aziendale sono obbligatorie.");
    }
    this.category = category;
    this.purchaseDate = purchaseDate;
    this.purchasePrice = purchasePrice;
    this.residualValue = residualValue;
    this.usefulLifeMonths = usefulLifeMonths;
    this.notes = notes;
  }

  public static FleetAssetPurchase of(
      String assetCode,
      FleetAssetCategory category,
      String supplierInvoiceNumber,
      String assignedVehicleFleetNumber,
      String description,
      LocalDate purchaseDate,
      Money purchasePrice,
      Money residualValue,
      int usefulLifeMonths,
      Notes notes) {
    return new FleetAssetPurchase(
        assetCode,
        category,
        supplierInvoiceNumber,
        assignedVehicleFleetNumber,
        description,
        purchaseDate,
        purchasePrice,
        residualValue,
        usefulLifeMonths,
        notes);
  }

  public static FleetAssetPurchase vehicle(
      String assetCode,
      FleetAssetCategory category,
      String supplierInvoiceNumber,
      String assignedVehicleFleetNumber,
      String description,
      LocalDate purchaseDate,
      Money purchasePrice,
      Money residualValue,
      int usefulLifeMonths,
      Notes notes) {
    if (category == null || !category.isVehicleUnit()) {
      throw new IllegalArgumentException("La categoria deve essere un mezzo o rimorchio.");
    }
    return of(
        assetCode,
        category,
        supplierInvoiceNumber,
        assignedVehicleFleetNumber,
        description,
        purchaseDate,
        purchasePrice,
        residualValue,
        usefulLifeMonths,
        notes);
  }

  private static String validateCode(String code, String nullMessage) {
    if (code == null) {
      throw new IllegalArgumentException(nullMessage);
    }
    String normalized = code.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
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

  private static String normalizeOptionalCode(String code) {
    if (code == null || code.trim().isEmpty()) {
      return null;
    }
    return validateCode(code, "Il codice opzionale non può essere vuoto.");
  }

  private static String validateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("La descrizione bene aziendale è obbligatoria.");
    }
    String normalized = description.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("La descrizione bene aziendale non può essere vuota.");
    }
    if (normalized.length() > MAX_DESCRIPTION_LENGTH) {
      throw new IllegalArgumentException(
          "La descrizione bene aziendale non può superare "
              + MAX_DESCRIPTION_LENGTH
              + " caratteri.");
    }
    return normalized;
  }

  public String getAssetCode() {
    return assetCode;
  }

  public FleetAssetCategory getCategory() {
    return category;
  }

  public String getSupplierInvoiceNumber() {
    return supplierInvoiceNumber;
  }

  public String getAssignedVehicleFleetNumber() {
    return assignedVehicleFleetNumber;
  }

  public boolean isAssignedToVehicle() {
    return assignedVehicleFleetNumber != null;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getPurchaseDate() {
    return purchaseDate;
  }

  public Money getPurchasePrice() {
    return purchasePrice;
  }

  public Money getResidualValue() {
    return residualValue;
  }

  public int getUsefulLifeMonths() {
    return usefulLifeMonths;
  }

  public Notes getNotes() {
    return notes;
  }

  public Money calculateDepreciableValue() {
    return purchasePrice.subtract(residualValue);
  }

  public Money calculateMonthlyDepreciation() {
    BigDecimal monthly =
        calculateDepreciableValue()
            .getAmount()
            .divide(BigDecimal.valueOf(usefulLifeMonths), 2, RoundingMode.HALF_UP);
    return Money.of(monthly, purchasePrice.getCurrency());
  }

  public Money calculateDepreciationForMonths(int months) {
    if (months < 0) {
      throw new IllegalArgumentException("I mesi di ammortamento non possono essere negativi.");
    }
    BigDecimal amount =
        calculateMonthlyDepreciation().getAmount().multiply(BigDecimal.valueOf(months));
    BigDecimal max = calculateDepreciableValue().getAmount();
    if (amount.compareTo(max) > 0) {
      amount = max;
    }
    return Money.of(amount, purchasePrice.getCurrency());
  }

  public Money calculateDepreciationForDays(long days) {
    if (days < 0) {
      throw new IllegalArgumentException("I giorni di ammortamento non possono essere negativi.");
    }
    BigDecimal totalDays = BigDecimal.valueOf(usefulLifeMonths).multiply(BigDecimal.valueOf(30));
    BigDecimal amount =
        calculateDepreciableValue()
            .getAmount()
            .multiply(BigDecimal.valueOf(days))
            .divide(totalDays, 2, RoundingMode.HALF_UP);
    BigDecimal max = calculateDepreciableValue().getAmount();
    if (amount.compareTo(max) > 0) {
      amount = max;
    }
    return Money.of(amount, purchasePrice.getCurrency());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FleetAssetPurchase that)) return false;
    return usefulLifeMonths == that.usefulLifeMonths
        && assetCode.equals(that.assetCode)
        && category == that.category
        && supplierInvoiceNumber.equals(that.supplierInvoiceNumber)
        && Objects.equals(assignedVehicleFleetNumber, that.assignedVehicleFleetNumber)
        && description.equals(that.description)
        && purchaseDate.equals(that.purchaseDate)
        && purchasePrice.equals(that.purchasePrice)
        && residualValue.equals(that.residualValue)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        assetCode,
        category,
        supplierInvoiceNumber,
        assignedVehicleFleetNumber,
        description,
        purchaseDate,
        purchasePrice,
        residualValue,
        usefulLifeMonths,
        notes);
  }
}
