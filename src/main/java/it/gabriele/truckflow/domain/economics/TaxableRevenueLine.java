package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;

/**
 * Riga ricavo cliente con IVA. Il ricavo vero è l'imponibile; l'IVA incassata è debito verso
 * l'erario.
 */
public final class TaxableRevenueLine {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_DESCRIPTION_LENGTH = 200;

  private final String lineCode;
  private final MissionRevenueType type;
  private final String description;
  private final VatBreakdown price;
  private final Notes notes;

  private TaxableRevenueLine(
      String lineCode,
      MissionRevenueType type,
      String description,
      VatBreakdown price,
      Notes notes) {
    this.lineCode = validateCode(lineCode, "Il codice riga ricavo imponibile è obbligatorio.");
    if (type == null) {
      throw new IllegalArgumentException("Il tipo ricavo imponibile è obbligatorio.");
    }
    this.description = validateDescription(description);
    if (price == null) {
      throw new IllegalArgumentException("Il prezzo imponibile è obbligatorio.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note riga ricavo imponibile sono obbligatorie.");
    }
    this.type = type;
    this.price = price;
    this.notes = notes;
  }

  public static TaxableRevenueLine of(
      String lineCode,
      MissionRevenueType type,
      String description,
      VatBreakdown price,
      Notes notes) {
    return new TaxableRevenueLine(lineCode, type, description, price, notes);
  }

  public static TaxableRevenueLine taxableNet(
      String lineCode,
      MissionRevenueType type,
      String description,
      Money netAmount,
      VatRate vatRate,
      Notes notes) {
    return of(lineCode, type, description, VatBreakdown.taxableFromNet(netAmount, vatRate), notes);
  }

  public static TaxableRevenueLine baseTransportFee(
      String lineCode, String description, Money netAmount, VatRate vatRate, Notes notes) {
    return taxableNet(
        lineCode, MissionRevenueType.BASE_TRANSPORT_FEE, description, netAmount, vatRate, notes);
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
      throw new IllegalArgumentException("La descrizione riga ricavo imponibile è obbligatoria.");
    }
    String normalized = description.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException(
          "La descrizione riga ricavo imponibile non può essere vuota.");
    }
    if (normalized.length() > MAX_DESCRIPTION_LENGTH) {
      throw new IllegalArgumentException(
          "La descrizione riga ricavo imponibile non può superare "
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

  public VatBreakdown getPrice() {
    return price;
  }

  public Notes getNotes() {
    return notes;
  }

  public Money getNetAmount() {
    return price.getNetAmount();
  }

  public Money getVatAmount() {
    return price.getVatAmount();
  }

  public Money getGrossAmount() {
    return price.getGrossAmount();
  }

  public MissionRevenueLine toMissionRevenueLine() {
    return MissionRevenueLine.of(lineCode, type, description, getNetAmount(), notes);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TaxableRevenueLine that)) return false;
    return lineCode.equals(that.lineCode)
        && type == that.type
        && description.equals(that.description)
        && price.equals(that.price)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lineCode, type, description, price, notes);
  }
}
