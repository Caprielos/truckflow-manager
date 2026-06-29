package it.gabriele.truckflow.domain.payroll;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.math.BigDecimal;
import java.util.Objects;

/** Riga paga calcolata per una missione specifica. */
public final class DriverMissionPayLine {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_DESCRIPTION_LENGTH = 200;

  private final String lineCode;
  private final DriverPayComponentType componentType;
  private final DriverPayUnit unit;
  private final String description;
  private final BigDecimal quantity;
  private final Money amount;
  private final Notes notes;

  private DriverMissionPayLine(
      String lineCode,
      DriverPayComponentType componentType,
      DriverPayUnit unit,
      String description,
      BigDecimal quantity,
      Money amount,
      Notes notes) {
    this.lineCode = validateCode(lineCode);
    if (componentType == null) {
      throw new IllegalArgumentException("Il tipo riga paga autista è obbligatorio.");
    }
    if (unit == null) {
      throw new IllegalArgumentException("L'unità riga paga autista è obbligatoria.");
    }
    this.description = validateDescription(description);
    if (quantity == null) {
      throw new IllegalArgumentException("La quantità riga paga autista è obbligatoria.");
    }
    if (quantity.signum() < 0) {
      throw new IllegalArgumentException("La quantità riga paga autista non può essere negativa.");
    }
    if (amount == null) {
      throw new IllegalArgumentException("L'importo riga paga autista è obbligatorio.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note riga paga autista sono obbligatorie.");
    }
    this.componentType = componentType;
    this.unit = unit;
    this.quantity = quantity;
    this.amount = amount;
    this.notes = notes;
  }

  public static DriverMissionPayLine of(
      String lineCode,
      DriverPayComponentType componentType,
      DriverPayUnit unit,
      String description,
      BigDecimal quantity,
      Money amount,
      Notes notes) {
    return new DriverMissionPayLine(
        lineCode, componentType, unit, description, quantity, amount, notes);
  }

  private static String validateCode(String code) {
    if (code == null) {
      throw new IllegalArgumentException("Il codice riga paga è obbligatorio.");
    }
    String normalized = code.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("Il codice riga paga non può essere vuoto.");
    }
    if (normalized.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice riga paga non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice riga paga può contenere solo lettere, numeri, trattini e underscore.");
    }
    return normalized;
  }

  private static String validateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("La descrizione riga paga è obbligatoria.");
    }
    String normalized = description.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("La descrizione riga paga non può essere vuota.");
    }
    if (normalized.length() > MAX_DESCRIPTION_LENGTH) {
      throw new IllegalArgumentException(
          "La descrizione riga paga non può superare " + MAX_DESCRIPTION_LENGTH + " caratteri.");
    }
    return normalized;
  }

  public String getLineCode() {
    return lineCode;
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

  public BigDecimal getQuantity() {
    return quantity;
  }

  public Money getAmount() {
    return amount;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isAllowanceOrReimbursement() {
    return componentType.isAllowanceOrReimbursement();
  }

  public boolean isEmployerCost() {
    return componentType.isEmployerCost();
  }

  private BigDecimal normalizedQuantity() {
    return quantity.stripTrailingZeros();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DriverMissionPayLine that)) return false;
    return lineCode.equals(that.lineCode)
        && componentType == that.componentType
        && unit == that.unit
        && description.equals(that.description)
        && normalizedQuantity().compareTo(that.normalizedQuantity()) == 0
        && amount.equals(that.amount)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        lineCode, componentType, unit, description, normalizedQuantity(), amount, notes);
  }
}
