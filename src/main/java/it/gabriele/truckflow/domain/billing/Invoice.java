package it.gabriele.truckflow.domain.billing;

import it.gabriele.truckflow.domain.pricing.PriceBreakdown;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.Objects;

/** Rappresenta una fattura collegata a un cliente, una spedizione e un dettaglio prezzo. */
public final class Invoice {

  private static final int MAX_CODE_LENGTH = 50;

  private final String invoiceNumber;
  private final String customerCode;
  private final String shipmentNumber;
  private final PriceBreakdown priceBreakdown;
  private final LocalDate issueDate;
  private final LocalDate dueDate;
  private final InvoiceStatus status;
  private final Notes notes;

  private Invoice(
      String invoiceNumber,
      String customerCode,
      String shipmentNumber,
      PriceBreakdown priceBreakdown,
      LocalDate issueDate,
      LocalDate dueDate,
      InvoiceStatus status,
      Notes notes) {
    this.invoiceNumber = validateCode(invoiceNumber, "Il numero fattura è obbligatorio.");
    this.customerCode = validateCode(customerCode, "Il codice cliente è obbligatorio.");
    this.shipmentNumber = validateCode(shipmentNumber, "Il numero spedizione è obbligatorio.");

    if (priceBreakdown == null) {
      throw new IllegalArgumentException("Il dettaglio prezzo della fattura è obbligatorio.");
    }

    if (issueDate == null) {
      throw new IllegalArgumentException("La data emissione fattura è obbligatoria.");
    }

    if (dueDate == null) {
      throw new IllegalArgumentException("La data scadenza fattura è obbligatoria.");
    }

    if (dueDate.isBefore(issueDate)) {
      throw new IllegalArgumentException(
          "La data scadenza non può essere precedente alla data emissione.");
    }

    if (status == null) {
      throw new IllegalArgumentException("Lo stato fattura è obbligatorio.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note fattura sono obbligatorie.");
    }

    this.priceBreakdown = priceBreakdown;
    this.issueDate = issueDate;
    this.dueDate = dueDate;
    this.status = status;
    this.notes = notes;
  }

  public static Invoice draft(
      String invoiceNumber,
      String customerCode,
      String shipmentNumber,
      PriceBreakdown priceBreakdown,
      LocalDate issueDate,
      LocalDate dueDate,
      Notes notes) {
    return new Invoice(
        invoiceNumber,
        customerCode,
        shipmentNumber,
        priceBreakdown,
        issueDate,
        dueDate,
        InvoiceStatus.DRAFT,
        notes);
  }

  public static Invoice issued(
      String invoiceNumber,
      String customerCode,
      String shipmentNumber,
      PriceBreakdown priceBreakdown,
      LocalDate issueDate,
      LocalDate dueDate,
      Notes notes) {
    return new Invoice(
        invoiceNumber,
        customerCode,
        shipmentNumber,
        priceBreakdown,
        issueDate,
        dueDate,
        InvoiceStatus.ISSUED,
        notes);
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

  public Invoice issue() {
    if (!BillingRules.canBeIssued(this)) {
      throw new IllegalStateException("La fattura non può essere emessa.");
    }

    return new Invoice(
        invoiceNumber,
        customerCode,
        shipmentNumber,
        priceBreakdown,
        issueDate,
        dueDate,
        InvoiceStatus.ISSUED,
        notes);
  }

  public Invoice markPaid() {
    if (!BillingRules.canBeMarkedPaid(this)) {
      throw new IllegalStateException("La fattura non può essere marcata come pagata.");
    }

    return new Invoice(
        invoiceNumber,
        customerCode,
        shipmentNumber,
        priceBreakdown,
        issueDate,
        dueDate,
        InvoiceStatus.PAID,
        notes);
  }

  public Invoice cancel() {
    if (!BillingRules.canBeCancelled(this)) {
      throw new IllegalStateException("La fattura non può essere cancellata.");
    }

    return new Invoice(
        invoiceNumber,
        customerCode,
        shipmentNumber,
        priceBreakdown,
        issueDate,
        dueDate,
        InvoiceStatus.CANCELLED,
        notes);
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public String getShipmentNumber() {
    return shipmentNumber;
  }

  public PriceBreakdown getPriceBreakdown() {
    return priceBreakdown;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public InvoiceStatus getStatus() {
    return status;
  }

  public Notes getNotes() {
    return notes;
  }

  public Money calculateTotal() {
    return priceBreakdown.calculateTotal();
  }

  public boolean isDraft() {
    return status == InvoiceStatus.DRAFT;
  }

  public boolean isIssued() {
    return status == InvoiceStatus.ISSUED;
  }

  public boolean isPaid() {
    return status == InvoiceStatus.PAID;
  }

  public boolean isCancelled() {
    return status == InvoiceStatus.CANCELLED;
  }

  public boolean isTerminal() {
    return status.isTerminal();
  }

  public boolean canReceivePayment() {
    return status.canReceivePayment();
  }

  public boolean hasNotes() {
    return notes.hasText();
  }

  public String formatSingleLine() {
    return invoiceNumber
        + " - customer: "
        + customerCode
        + " - shipment: "
        + shipmentNumber
        + " - total: "
        + calculateTotal()
        + " - "
        + status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Invoice invoice)) return false;
    return invoiceNumber.equals(invoice.invoiceNumber)
        && customerCode.equals(invoice.customerCode)
        && shipmentNumber.equals(invoice.shipmentNumber)
        && priceBreakdown.equals(invoice.priceBreakdown)
        && issueDate.equals(invoice.issueDate)
        && dueDate.equals(invoice.dueDate)
        && status == invoice.status
        && notes.equals(invoice.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        invoiceNumber,
        customerCode,
        shipmentNumber,
        priceBreakdown,
        issueDate,
        dueDate,
        status,
        notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
