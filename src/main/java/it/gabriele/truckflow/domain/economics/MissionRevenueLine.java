package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.pricing.PricingLine;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;

/**
 * Voce ricavo collegata a una missione: ciò che si fattura o si prevede di fatturare al cliente.
 */
public final class MissionRevenueLine {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_DESCRIPTION_LENGTH = 200;

  private final String lineCode;
  private final MissionRevenueType type;
  private final String description;
  private final Money amount;
  private final Notes notes;

  private MissionRevenueLine(
      String lineCode, MissionRevenueType type, String description, Money amount, Notes notes) {
    this.lineCode = validateCode(lineCode, "Il codice ricavo missione è obbligatorio.");
    if (type == null) {
      throw new IllegalArgumentException("Il tipo ricavo missione è obbligatorio.");
    }
    this.description = validateDescription(description);
    if (amount == null) {
      throw new IllegalArgumentException("L'importo ricavo missione è obbligatorio.");
    }
    if (amount.getAmount().signum() == 0) {
      throw new IllegalArgumentException("L'importo ricavo missione deve essere maggiore di zero.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note ricavo missione sono obbligatorie.");
    }
    this.type = type;
    this.amount = amount;
    this.notes = notes;
  }

  public static MissionRevenueLine of(
      String lineCode, MissionRevenueType type, String description, Money amount, Notes notes) {
    return new MissionRevenueLine(lineCode, type, description, amount, notes);
  }

  public static MissionRevenueLine baseTransportFee(
      String lineCode, String description, Money amount, Notes notes) {
    return of(lineCode, MissionRevenueType.BASE_TRANSPORT_FEE, description, amount, notes);
  }

  public static MissionRevenueLine fromPricingLine(
      String lineCode, MissionRevenueType type, PricingLine pricingLine, Notes notes) {
    if (pricingLine == null) {
      throw new IllegalArgumentException("La voce prezzo è obbligatoria.");
    }
    return of(lineCode, type, pricingLine.getDescription(), pricingLine.getAmount(), notes);
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

  private static String validateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("La descrizione ricavo missione è obbligatoria.");
    }
    String normalized = description.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("La descrizione ricavo missione non può essere vuota.");
    }
    if (normalized.length() > MAX_DESCRIPTION_LENGTH) {
      throw new IllegalArgumentException(
          "La descrizione ricavo missione non può superare "
              + MAX_DESCRIPTION_LENGTH
              + " caratteri.");
    }
    return normalized;
  }

  public String getLineCode() {
    return lineCode;
  }

  public MissionRevenueType getType() {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MissionRevenueLine that)) return false;
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
}
