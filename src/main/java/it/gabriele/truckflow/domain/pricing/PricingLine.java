package it.gabriele.truckflow.domain.pricing;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;

/** Singola voce prezzo di un preventivo. */
public final class PricingLine {

  private static final int MAX_LINE_CODE_LENGTH = 50;
  private static final int MAX_DESCRIPTION_LENGTH = 200;

  private final String lineCode;
  private final PricingLineType type;
  private final String description;
  private final Money amount;
  private final Notes notes;

  private PricingLine(
      String lineCode, PricingLineType type, String description, Money amount, Notes notes) {
    this.lineCode = validateLineCode(lineCode);

    if (type == null) {
      throw new IllegalArgumentException("Il tipo voce prezzo è obbligatorio.");
    }

    this.description = validateDescription(description);

    if (amount == null) {
      throw new IllegalArgumentException("L'importo della voce prezzo è obbligatorio.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note della voce prezzo sono obbligatorie.");
    }

    this.type = type;
    this.amount = amount;
    this.notes = notes;
  }

  public static PricingLine of(
      String lineCode, PricingLineType type, String description, Money amount, Notes notes) {
    return new PricingLine(lineCode, type, description, amount, notes);
  }

  public static PricingLine baseFreight(
      String lineCode, String description, Money amount, Notes notes) {
    return of(lineCode, PricingLineType.BASE_FREIGHT, description, amount, notes);
  }

  public static PricingLine surcharge(
      String lineCode, PricingLineType type, String description, Money amount, Notes notes) {
    if (type == null || !type.isSurcharge()) {
      throw new IllegalArgumentException("Il tipo voce deve essere un supplemento.");
    }

    return of(lineCode, type, description, amount, notes);
  }

  public static PricingLine discount(
      String lineCode, String description, Money amount, Notes notes) {
    return of(lineCode, PricingLineType.DISCOUNT, description, amount, notes);
  }

  public static PricingLine fuelFromEstimate(
      String lineCode, RouteCostEstimate estimate, Notes notes) {
    validateEstimate(estimate);
    return surcharge(
        lineCode,
        PricingLineType.FUEL_SURCHARGE,
        "Costo carburante stimato da " + estimate.getSource(),
        estimate.getEstimatedFuelCost(),
        notes);
  }

  public static PricingLine tollsFromEstimate(
      String lineCode, RouteCostEstimate estimate, Notes notes) {
    validateEstimate(estimate);
    return surcharge(
        lineCode,
        PricingLineType.TOLL_CHARGE,
        "Costo pedaggi stimato da " + estimate.getSource(),
        estimate.getEstimatedTollCost(),
        notes);
  }

  public static PricingLine vehicleWearFromEstimate(
      String lineCode, RouteCostEstimate estimate, Notes notes) {
    validateEstimate(estimate);
    return surcharge(
        lineCode,
        PricingLineType.VEHICLE_WEAR_CHARGE,
        "Costo usura mezzo stimato da " + estimate.getSource(),
        estimate.getEstimatedVehicleWearCost(),
        notes);
  }

  private static void validateEstimate(RouteCostEstimate estimate) {
    if (estimate == null) {
      throw new IllegalArgumentException("La stima costi percorso è obbligatoria.");
    }
  }

  private static String validateLineCode(String lineCode) {
    if (lineCode == null) {
      throw new IllegalArgumentException("Il codice voce prezzo è obbligatorio.");
    }

    String normalizedLineCode = lineCode.trim().toUpperCase();

    if (normalizedLineCode.isEmpty()) {
      throw new IllegalArgumentException("Il codice voce prezzo non può essere vuoto.");
    }

    if (normalizedLineCode.length() > MAX_LINE_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice voce prezzo non può superare " + MAX_LINE_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedLineCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice voce prezzo può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedLineCode;
  }

  private static String validateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("La descrizione della voce prezzo è obbligatoria.");
    }

    String normalizedDescription = description.trim();

    if (normalizedDescription.isEmpty()) {
      throw new IllegalArgumentException("La descrizione della voce prezzo non può essere vuota.");
    }

    if (normalizedDescription.length() > MAX_DESCRIPTION_LENGTH) {
      throw new IllegalArgumentException(
          "La descrizione della voce prezzo non può superare "
              + MAX_DESCRIPTION_LENGTH
              + " caratteri.");
    }

    return normalizedDescription;
  }

  public String getLineCode() {
    return lineCode;
  }

  public PricingLineType getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public Money getAmount() {
    return amount;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isSurcharge() {
    return type.isSurcharge();
  }

  public boolean isDiscount() {
    return type.isDiscount();
  }

  public boolean increasesTotal() {
    return type.increasesTotal();
  }

  public boolean decreasesTotal() {
    return type.decreasesTotal();
  }

  public boolean hasNotes() {
    return notes.hasText();
  }

  public String formatSingleLine() {
    return lineCode + " - " + type + " - " + amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PricingLine that)) return false;
    return lineCode.equals(that.lineCode)
        && type == that.type
        && description.equals(that.description)
        && amount.equals(that.amount)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lineCode, type, description, amount, notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
