package it.gabriele.truckflow.domain.order;

/**
 * Rappresenta lo stato di un ordine di trasporto.
 */
public enum TransportOrderStatus {

    DRAFT(false),
    SUBMITTED(false),
    ACCEPTED(false),
    REJECTED(true),
    CANCELLED(true);

    private final boolean terminal;

    TransportOrderStatus(boolean terminal) {
        this.terminal = terminal;
    }

    public boolean isTerminal() {
        return terminal;
    }
}
