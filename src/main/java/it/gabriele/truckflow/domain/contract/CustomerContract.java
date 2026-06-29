package it.gabriele.truckflow.domain.contract;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.Objects;

/** Contratto cliente: collega un cliente a un listino valido in un certo periodo. */
public final class CustomerContract {

  private static final int MAX_CODE_LENGTH = 50;

  private final String contractCode;
  private final String customerCode;
  private final DateRange validity;
  private final ContractRateCard rateCard;
  private final boolean active;
  private final Notes notes;

  private CustomerContract(
      String contractCode,
      String customerCode,
      DateRange validity,
      ContractRateCard rateCard,
      boolean active,
      Notes notes) {
    this.contractCode = validateCode(contractCode, "Il codice contratto è obbligatorio.");
    this.customerCode = validateCode(customerCode, "Il codice cliente contratto è obbligatorio.");
    if (validity == null) {
      throw new IllegalArgumentException("La validità contratto è obbligatoria.");
    }
    if (rateCard == null) {
      throw new IllegalArgumentException("Il listino contratto è obbligatorio.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note contratto sono obbligatorie.");
    }
    this.validity = validity;
    this.rateCard = rateCard;
    this.active = active;
    this.notes = notes;
  }

  public static CustomerContract active(
      String contractCode,
      String customerCode,
      DateRange validity,
      ContractRateCard rateCard,
      Notes notes) {
    return new CustomerContract(contractCode, customerCode, validity, rateCard, true, notes);
  }

  public static CustomerContract inactive(
      String contractCode,
      String customerCode,
      DateRange validity,
      ContractRateCard rateCard,
      Notes notes) {
    return new CustomerContract(contractCode, customerCode, validity, rateCard, false, notes);
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

  public String getContractCode() {
    return contractCode;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public DateRange getValidity() {
    return validity;
  }

  public ContractRateCard getRateCard() {
    return rateCard;
  }

  public boolean isActive() {
    return active;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isValidOn(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("La data verifica contratto è obbligatoria.");
    }
    return active && validity.contains(date);
  }

  public boolean hasTariff(TariffRuleType type) {
    return rateCard.containsRule(type);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CustomerContract that)) return false;
    return active == that.active
        && contractCode.equals(that.contractCode)
        && customerCode.equals(that.customerCode)
        && validity.equals(that.validity)
        && rateCard.equals(that.rateCard)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contractCode, customerCode, validity, rateCard, active, notes);
  }
}
