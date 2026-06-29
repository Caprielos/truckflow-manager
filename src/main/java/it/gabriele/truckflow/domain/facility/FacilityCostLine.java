package it.gabriele.truckflow.domain.facility;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Rappresenta una voce di costo legata a una struttura aziendale. Esempi: affitto mensile, tassa
 * annua, utenze, videosorveglianza, manutenzione piazzale.
 */
public final class FacilityCostLine {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_DESCRIPTION_LENGTH = 200;

  private final String code;
  private final FacilityCostType type;
  private final String description;
  private final Money amount;
  private final FacilityCostFrequency frequency;
  private final DateRange coveragePeriod;
  private final Notes notes;

  private FacilityCostLine(
      String code,
      FacilityCostType type,
      String description,
      Money amount,
      FacilityCostFrequency frequency,
      DateRange coveragePeriod,
      Notes notes) {
    this.code = validateCode(code);
    this.type = validateType(type);
    this.description = validateDescription(description);
    this.amount = validateAmount(amount);
    this.frequency = validateFrequency(frequency);
    this.coveragePeriod = coveragePeriod;
    this.notes = validateNotes(notes);
  }

  public static FacilityCostLine of(
      String code,
      FacilityCostType type,
      String description,
      Money amount,
      FacilityCostFrequency frequency,
      DateRange coveragePeriod,
      Notes notes) {
    return new FacilityCostLine(code, type, description, amount, frequency, coveragePeriod, notes);
  }

  public static FacilityCostLine monthly(
      String code, FacilityCostType type, String description, Money amount, Notes notes) {
    return new FacilityCostLine(
        code, type, description, amount, FacilityCostFrequency.MONTHLY, null, notes);
  }

  public static FacilityCostLine yearly(
      String code, FacilityCostType type, String description, Money amount, Notes notes) {
    return new FacilityCostLine(
        code, type, description, amount, FacilityCostFrequency.YEARLY, null, notes);
  }

  public static FacilityCostLine oneTime(
      String code, FacilityCostType type, String description, Money amount, Notes notes) {
    return new FacilityCostLine(
        code, type, description, amount, FacilityCostFrequency.ONE_TIME, null, notes);
  }

  private static String validateCode(String code) {
    if (code == null) {
      throw new IllegalArgumentException("Il codice costo struttura è obbligatorio.");
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException("Il codice costo struttura non può essere vuoto.");
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice costo struttura non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice costo struttura può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  private static FacilityCostType validateType(FacilityCostType type) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo costo struttura è obbligatorio.");
    }
    return type;
  }

  private static String validateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("La descrizione costo struttura è obbligatoria.");
    }

    String normalizedDescription = description.trim();

    if (normalizedDescription.isEmpty()) {
      throw new IllegalArgumentException("La descrizione costo struttura non può essere vuota.");
    }

    if (normalizedDescription.length() > MAX_DESCRIPTION_LENGTH) {
      throw new IllegalArgumentException(
          "La descrizione costo struttura non può superare "
              + MAX_DESCRIPTION_LENGTH
              + " caratteri.");
    }

    return normalizedDescription;
  }

  private static Money validateAmount(Money amount) {
    if (amount == null) {
      throw new IllegalArgumentException("L'importo costo struttura è obbligatorio.");
    }
    return amount;
  }

  private static FacilityCostFrequency validateFrequency(FacilityCostFrequency frequency) {
    if (frequency == null) {
      throw new IllegalArgumentException("La frequenza costo struttura è obbligatoria.");
    }
    return frequency;
  }

  private static Notes validateNotes(Notes notes) {
    if (notes == null) {
      throw new IllegalArgumentException("Le note costo struttura sono obbligatorie.");
    }
    return notes;
  }

  public String getCode() {
    return code;
  }

  public FacilityCostType getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public Money getAmount() {
    return amount;
  }

  public FacilityCostFrequency getFrequency() {
    return frequency;
  }

  public DateRange getCoveragePeriod() {
    return coveragePeriod;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isRecurring() {
    return frequency.isRecurring();
  }

  public boolean hasCoveragePeriod() {
    return coveragePeriod != null;
  }

  public Money monthlyEquivalent() {
    return switch (frequency) {
      case MONTHLY -> amount;
      case YEARLY ->
          Money.of(
              amount.getAmount().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP),
              amount.getCurrency());
      case ONE_TIME -> Money.of(BigDecimal.ZERO, amount.getCurrency());
    };
  }

  public boolean isRentLikeCost() {
    return type == FacilityCostType.RENT || type == FacilityCostType.LAND_LEASE;
  }

  public boolean isTaxLikeCost() {
    return type == FacilityCostType.PROPERTY_TAX || type == FacilityCostType.PERMIT;
  }

  public boolean isUtilityLikeCost() {
    return type == FacilityCostType.UTILITIES
        || type == FacilityCostType.ELECTRICITY
        || type == FacilityCostType.WATER
        || type == FacilityCostType.WASTE_MANAGEMENT
        || type == FacilityCostType.CHARGING_STATION_ENERGY;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FacilityCostLine that)) return false;
    return code.equals(that.code)
        && type == that.type
        && description.equals(that.description)
        && amount.equals(that.amount)
        && frequency == that.frequency
        && Objects.equals(coveragePeriod, that.coveragePeriod)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, type, description, amount, frequency, coveragePeriod, notes);
  }

  @Override
  public String toString() {
    return code + " - " + type + " - " + amount + " - " + frequency;
  }
}
