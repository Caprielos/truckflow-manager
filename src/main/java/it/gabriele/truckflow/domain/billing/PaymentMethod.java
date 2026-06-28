package it.gabriele.truckflow.domain.billing;

/**
 * Metodo di pagamento.
 */
public enum PaymentMethod {

    BANK_TRANSFER(true),
    CARD(true),
    CASH(false),
    DIRECT_DEBIT(true),
    CREDIT_NOTE(false),
    OTHER(false);

    private final boolean electronic;

    PaymentMethod(boolean electronic) {
        this.electronic = electronic;
    }

    public boolean isElectronic() {
        return electronic;
    }
}
