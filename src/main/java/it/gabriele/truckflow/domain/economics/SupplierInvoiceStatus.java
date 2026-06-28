package it.gabriele.truckflow.domain.economics;

/**
 * Stato di una fattura fornitore.
 */
public enum SupplierInvoiceStatus {
    RECEIVED,
    APPROVED,
    PAID,
    DISPUTED,
    CANCELLED;

    public boolean isPayable() {
        return this == RECEIVED || this == APPROVED;
    }
}
