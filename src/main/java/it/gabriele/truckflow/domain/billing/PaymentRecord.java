package it.gabriele.truckflow.domain.billing;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.Objects;

/** Rappresenta un pagamento ricevuto per una fattura. */
public final class PaymentRecord {

  private static final int MAX_CODE_LENGTH = 50;

  private final String paymentNumber;
  private final String invoiceNumber;
  private final Money amount;
  private final PaymentMethod method;
  private final LocalDate receivedDate;
  private final Notes notes;

  private PaymentRecord(
      String paymentNumber,
      String invoiceNumber,
      Money amount,
      PaymentMethod method,
      LocalDate receivedDate,
      Notes notes) {
    this.paymentNumber = validateCode(paymentNumber, "Il numero pagamento è obbligatorio.");
    this.invoiceNumber =
        validateCode(invoiceNumber, "Il numero fattura del pagamento è obbligatorio.");

    if (amount == null) {
      throw new IllegalArgumentException("L'importo del pagamento è obbligatorio.");
    }

    if (method == null) {
      throw new IllegalArgumentException("Il metodo di pagamento è obbligatorio.");
    }

    if (receivedDate == null) {
      throw new IllegalArgumentException("La data ricezione pagamento è obbligatoria.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note pagamento sono obbligatorie.");
    }

    this.amount = amount;
    this.method = method;
    this.receivedDate = receivedDate;
    this.notes = notes;
  }

  public static PaymentRecord of(
      String paymentNumber,
      String invoiceNumber,
      Money amount,
      PaymentMethod method,
      LocalDate receivedDate,
      Notes notes) {
    return new PaymentRecord(paymentNumber, invoiceNumber, amount, method, receivedDate, notes);
  }

  public static PaymentRecord bankTransfer(
      String paymentNumber,
      String invoiceNumber,
      Money amount,
      LocalDate receivedDate,
      Notes notes) {
    return of(
        paymentNumber, invoiceNumber, amount, PaymentMethod.BANK_TRANSFER, receivedDate, notes);
  }

  private static String validateCode(String code, String nullMessage) {
    if (code == null) {
      throw new IllegalArgumentException(nullMessage);
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  public String getPaymentNumber() {
    return paymentNumber;
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public Money getAmount() {
    return amount;
  }

  public PaymentMethod getMethod() {
    return method;
  }

  public LocalDate getReceivedDate() {
    return receivedDate;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isElectronicPayment() {
    return method.isElectronic();
  }

  public boolean hasNotes() {
    return notes.hasText();
  }

  public boolean isForInvoice(Invoice invoice) {
    if (invoice == null) {
      throw new IllegalArgumentException("La fattura da verificare è obbligatoria.");
    }

    return invoiceNumber.equals(invoice.getInvoiceNumber());
  }

  public boolean isForInvoiceNumber(String invoiceNumber) {
    return this.invoiceNumber.equals(
        validateCode(invoiceNumber, "Il numero fattura da verificare è obbligatorio."));
  }

  public String formatSingleLine() {
    return paymentNumber + " - invoice: " + invoiceNumber + " - amount: " + amount + " - " + method;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PaymentRecord that)) return false;
    return paymentNumber.equals(that.paymentNumber)
        && invoiceNumber.equals(that.invoiceNumber)
        && amount.equals(that.amount)
        && method == that.method
        && receivedDate.equals(that.receivedDate)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(paymentNumber, invoiceNumber, amount, method, receivedDate, notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
