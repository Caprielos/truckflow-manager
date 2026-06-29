package it.gabriele.truckflow.domain.contract;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.Percentage;
import java.util.Objects;

/**
 * Riga di listino contrattuale: può essere un importo fisso, una tariffa per unità o una
 * percentuale.
 */
public final class TariffRule {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_DESCRIPTION_LENGTH = 200;

  private final String ruleCode;
  private final TariffRuleType type;
  private final String description;
  private final ChargeUnit unit;
  private final Money amount;
  private final Percentage percentage;
  private final boolean mandatory;
  private final Notes notes;

  private TariffRule(
      String ruleCode,
      TariffRuleType type,
      String description,
      ChargeUnit unit,
      Money amount,
      Percentage percentage,
      boolean mandatory,
      Notes notes) {
    this.ruleCode = validateCode(ruleCode, "Il codice regola tariffaria è obbligatorio.");
    if (type == null) {
      throw new IllegalArgumentException("Il tipo regola tariffaria è obbligatorio.");
    }
    this.description = validateDescription(description);
    if (unit == null) {
      throw new IllegalArgumentException("L'unità tariffaria è obbligatoria.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note regola tariffaria sono obbligatorie.");
    }
    this.type = type;
    this.unit = unit;
    this.amount = amount;
    this.percentage = percentage;
    this.mandatory = mandatory;
    this.notes = notes;
    validatePricingMode();
  }

  public static TariffRule amount(
      String ruleCode,
      TariffRuleType type,
      String description,
      ChargeUnit unit,
      Money amount,
      boolean mandatory,
      Notes notes) {
    return new TariffRule(ruleCode, type, description, unit, amount, null, mandatory, notes);
  }

  public static TariffRule percentage(
      String ruleCode,
      TariffRuleType type,
      String description,
      Percentage percentage,
      boolean mandatory,
      Notes notes) {
    return new TariffRule(
        ruleCode,
        type,
        description,
        ChargeUnit.PERCENTAGE_OF_BASE_FEE,
        null,
        percentage,
        mandatory,
        notes);
  }

  private void validatePricingMode() {
    if (unit.isPercentageBased()) {
      if (percentage == null) {
        throw new IllegalArgumentException("Una tariffa percentuale richiede una percentuale.");
      }
      if (amount != null) {
        throw new IllegalArgumentException(
            "Una tariffa percentuale non deve avere un importo fisso.");
      }
      return;
    }

    if (amount == null) {
      throw new IllegalArgumentException("Una tariffa a importo richiede un importo.");
    }
    if (amount.getAmount().signum() == 0) {
      throw new IllegalArgumentException("L'importo tariffa deve essere maggiore di zero.");
    }
    if (percentage != null) {
      throw new IllegalArgumentException("Una tariffa a importo non deve avere percentuale.");
    }
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

  private static String validateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("La descrizione regola tariffaria è obbligatoria.");
    }
    String normalized = description.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("La descrizione regola tariffaria non può essere vuota.");
    }
    if (normalized.length() > MAX_DESCRIPTION_LENGTH) {
      throw new IllegalArgumentException(
          "La descrizione regola tariffaria non può superare "
              + MAX_DESCRIPTION_LENGTH
              + " caratteri.");
    }
    return normalized;
  }

  public String getRuleCode() {
    return ruleCode;
  }

  public TariffRuleType getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public ChargeUnit getUnit() {
    return unit;
  }

  public Money getAmount() {
    return amount;
  }

  public Percentage getPercentage() {
    return percentage;
  }

  public boolean isMandatory() {
    return mandatory;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean appliesTo(TariffRuleType type) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo regola da cercare è obbligatorio.");
    }
    return this.type == type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TariffRule that)) return false;
    return mandatory == that.mandatory
        && ruleCode.equals(that.ruleCode)
        && type == that.type
        && description.equals(that.description)
        && unit == that.unit
        && Objects.equals(amount, that.amount)
        && Objects.equals(percentage, that.percentage)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ruleCode, type, description, unit, amount, percentage, mandatory, notes);
  }
}
