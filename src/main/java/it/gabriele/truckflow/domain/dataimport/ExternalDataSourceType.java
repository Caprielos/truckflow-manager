package it.gabriele.truckflow.domain.dataimport;

/**
 * Fonti reali da cui importare costi, ricavi o eventi operativi.
 */
public enum ExternalDataSourceType {

    FUEL_CARD,
    TOLL_PROVIDER,
    TELEMATICS_PROVIDER,
    SUPPLIER_INVOICE_FILE,
    CUSTOMER_INVOICE_FILE,
    BANK_STATEMENT,
    PAYROLL_PROVIDER,
    MAINTENANCE_SYSTEM,
    TACHOGRAPH_EXPORT,
    CSV_FILE,
    MANUAL_UPLOAD,
    OTHER;

    public boolean usuallyContainsEconomicData() {
        return this == FUEL_CARD
                || this == TOLL_PROVIDER
                || this == SUPPLIER_INVOICE_FILE
                || this == CUSTOMER_INVOICE_FILE
                || this == BANK_STATEMENT
                || this == PAYROLL_PROVIDER
                || this == MAINTENANCE_SYSTEM;
    }
}
