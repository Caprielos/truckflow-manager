package it.gabriele.truckflow.domain.payroll;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.Percentage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Regola di paga configurabile: può essere oraria, giornaliera, a missione, rimborso puro o
 * percentuale sul costo base.
 */
public final class DriverPayRule {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_DESCRIPTION_LENGTH = 200;

  private final String ruleCode;
  private final DriverPayComponentType componentType;
  private final DriverPayUnit unit;
  private final String description;
  private final Money amount;
  private final Percentage percentage;
  private final Notes notes;

  private DriverPayRule(
      String ruleCode,
      DriverPayComponentType componentType,
      DriverPayUnit unit,
      String description,
      Money amount,
      Percentage percentage,
      Notes notes) {
    this.ruleCode = validateCode(ruleCode);
    if (componentType == null) {
      throw new IllegalArgumentException("Il tipo voce paga autista è obbligatorio.");
    }
    if (unit == null) {
      throw new IllegalArgumentException("L'unità di calcolo paga autista è obbligatoria.");
    }
    this.description = validateDescription(description);
    if (unit.usesPercentage()) {
      if (percentage == null) {
        throw new IllegalArgumentException("La percentuale della regola paga è obbligatoria.");
      }
      if (amount != null) {
        throw new IllegalArgumentException("Una regola percentuale non deve avere importo fisso.");
      }
    } else {
      if (amount == null) {
        throw new IllegalArgumentException("L'importo della regola paga è obbligatorio.");
      }
      if (amount.getAmount().signum() == 0) {
        throw new IllegalArgumentException(
            "L'importo della regola paga deve essere maggiore di zero.");
      }
      if (percentage != null) {
        throw new IllegalArgumentException("Una regola a importo non deve avere percentuale.");
      }
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note regola paga sono obbligatorie.");
    }
    this.componentType = componentType;
    this.unit = unit;
    this.amount = amount;
    this.percentage = percentage;
    this.notes = notes;
  }

  public static DriverPayRule amount(
      String ruleCode,
      DriverPayComponentType componentType,
      DriverPayUnit unit,
      String description,
      Money amount,
      Notes notes) {
    return new DriverPayRule(ruleCode, componentType, unit, description, amount, null, notes);
  }

  public static DriverPayRule percentageOfBase(
      String ruleCode,
      DriverPayComponentType componentType,
      String description,
      Percentage percentage,
      Notes notes) {
    return new DriverPayRule(
        ruleCode,
        componentType,
        DriverPayUnit.PERCENTAGE_OF_BASE,
        description,
        null,
        percentage,
        notes);
  }

  public DriverMissionPayLine calculateLine(BigDecimal quantity, Money baseAmount) {
    if (quantity == null) {
      throw new IllegalArgumentException("La quantità della regola paga è obbligatoria.");
    }
    if (quantity.signum() < 0) {
      throw new IllegalArgumentException("La quantità della regola paga non può essere negativa.");
    }

    Money calculatedAmount;
    if (unit.usesPercentage()) {
      if (baseAmount == null) {
        throw new IllegalArgumentException(
            "Il costo base è obbligatorio per calcolare una regola percentuale.");
      }
      calculatedAmount =
          Money.of(
              baseAmount
                  .getAmount()
                  .multiply(percentage.toMultiplier())
                  .setScale(2, RoundingMode.HALF_UP),
              baseAmount.getCurrency());
    } else {
      calculatedAmount =
          Money.of(
              amount.getAmount().multiply(quantity).setScale(2, RoundingMode.HALF_UP),
              amount.getCurrency());
    }

    return DriverMissionPayLine.of(
        ruleCode, componentType, unit, description, quantity, calculatedAmount, notes);
  }

  private static String validateCode(String code) {
    if (code == null) {
      throw new IllegalArgumentException("Il codice regola paga è obbligatorio.");
    }
    String normalized = code.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("Il codice regola paga non può essere vuoto.");
    }
    if (normalized.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice regola paga non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice regola paga può contenere solo lettere, numeri, trattini e underscore.");
    }
    return normalized;
  }

  private static String validateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("La descrizione regola paga è obbligatoria.");
    }
    String normalized = description.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("La descrizione regola paga non può essere vuota.");
    }
    if (normalized.length() > MAX_DESCRIPTION_LENGTH) {
      throw new IllegalArgumentException(
          "La descrizione regola paga non può superare " + MAX_DESCRIPTION_LENGTH + " caratteri.");
    }
    return normalized;
  }

  public String getRuleCode() {
    return ruleCode;
  }

  public DriverPayComponentType getComponentType() {
    return componentType;
  }

  public DriverPayUnit getUnit() {
    return unit;
  }

  public String getDescription() {
    return description;
  }

  public Money getAmount() {
    return amount;
  }

  public Percentage getPercentage() {
    return percentage;
  }

  public Notes getNotes() {
    return notes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DriverPayRule that)) return false;
    return ruleCode.equals(that.ruleCode)
        && componentType == that.componentType
        && unit == that.unit
        && description.equals(that.description)
        && Objects.equals(amount, that.amount)
        && Objects.equals(percentage, that.percentage)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ruleCode, componentType, unit, description, amount, percentage, notes);
  }
}
