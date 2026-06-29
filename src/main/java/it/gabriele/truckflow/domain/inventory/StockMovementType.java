package it.gabriele.truckflow.domain.inventory;

/**
 * Tipo di movimento magazzino.
 */
public enum StockMovementType {

    PURCHASE_IN(1),
    TRANSFER_IN(1),
    RETURN_FROM_VEHICLE(1),
    ADJUSTMENT_IN(1),
    TRANSFER_OUT(-1),
    CONSUMPTION_MAINTENANCE(-1),
    CONSUMPTION_MISSION(-1),
    INSTALLED_ON_VEHICLE(-1),
    SCRAP(-1),
    RETURN_TO_SUPPLIER(-1),
    ADJUSTMENT_OUT(-1);

    private final int sign;

    StockMovementType(int sign) {
        this.sign = sign;
    }

    public int getSign() {
        return sign;
    }

    public boolean increasesStock() {
        return sign > 0;
    }

    public boolean decreasesStock() {
        return sign < 0;
    }
}
