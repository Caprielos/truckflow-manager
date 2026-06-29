package it.gabriele.truckflow.domain.financeops;

/** Stato fattura elettronica. */
public enum ElectronicInvoiceStatus {
  DRAFT,
  READY_TO_SEND,
  SENT,
  ACCEPTED,
  REJECTED,
  PAID,
  CANCELLED
}
