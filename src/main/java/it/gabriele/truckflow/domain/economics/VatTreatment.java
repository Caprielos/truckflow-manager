package it.gabriele.truckflow.domain.economics;

/**
 * Trattamento IVA della riga economica.
 * Il dominio non fissa una normativa fiscale: conserva il trattamento usato nel calcolo.
 */
public enum VatTreatment {
    TAXABLE,
    EXEMPT,
    REVERSE_CHARGE,
    OUT_OF_SCOPE,
    NOT_DEDUCTIBLE;

    public boolean generatesVatAmount() {
        return this == TAXABLE || this == NOT_DEDUCTIBLE;
    }
}
