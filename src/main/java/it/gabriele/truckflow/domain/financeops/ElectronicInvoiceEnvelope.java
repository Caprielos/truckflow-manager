package it.gabriele.truckflow.domain.financeops;

import it.gabriele.truckflow.domain.shared.Money;
import java.time.LocalDate;

/** Busta fattura elettronica, indipendente dal provider nazionale. */
public record ElectronicInvoiceEnvelope(
    String invoiceCode,
    String customerOrSupplierCode,
    FinancialDocumentType documentType,
    ElectronicInvoiceStatus status,
    Money totalAmount,
    String externalTransmissionId,
    LocalDate issueDate,
    LocalDate dueDate) {

  public ElectronicInvoiceEnvelope {
    invoiceCode = normalize(invoiceCode, "Il codice fattura è obbligatorio.");
    customerOrSupplierCode =
        normalize(customerOrSupplierCode, "Il codice soggetto è obbligatorio.");
    if (documentType == null) {
      throw new IllegalArgumentException("Il tipo documento è obbligatorio.");
    }
    if (status == null) {
      throw new IllegalArgumentException("Lo stato fattura è obbligatorio.");
    }
    if (totalAmount == null) {
      throw new IllegalArgumentException("L'importo totale è obbligatorio.");
    }
    externalTransmissionId = externalTransmissionId == null ? "" : externalTransmissionId.trim();
    if (issueDate == null) {
      throw new IllegalArgumentException("La data emissione è obbligatoria.");
    }
    if (dueDate != null && dueDate.isBefore(issueDate)) {
      throw new IllegalArgumentException("La scadenza non può precedere l'emissione.");
    }
  }

  public boolean isRejected() {
    return status == ElectronicInvoiceStatus.REJECTED;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
