package it.gabriele.truckflow.domain.financeops;

import it.gabriele.truckflow.domain.shared.Money;
import java.time.LocalDate;

/** Movimento bancario importato per riconciliazione. */
public record BankTransaction(
    String transactionCode,
    BankTransactionType transactionType,
    LocalDate bookingDate,
    Money amount,
    String counterpartyCode,
    String referenceText,
    boolean reconciled) {

  public BankTransaction {
    transactionCode = normalize(transactionCode, "Il codice movimento bancario è obbligatorio.");
    if (transactionType == null) {
      throw new IllegalArgumentException("Il tipo movimento è obbligatorio.");
    }
    if (bookingDate == null) {
      throw new IllegalArgumentException("La data contabile è obbligatoria.");
    }
    if (amount == null) {
      throw new IllegalArgumentException("L'importo movimento è obbligatorio.");
    }
    counterpartyCode = counterpartyCode == null ? "" : counterpartyCode.trim().toUpperCase();
    referenceText = referenceText == null ? "" : referenceText.trim();
  }

  public boolean requiresManualReconciliation() {
    return !reconciled && transactionType == BankTransactionType.UNKNOWN;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
