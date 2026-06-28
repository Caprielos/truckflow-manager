package it.gabriele.truckflow.domain.pricing;

/**
 * Tipo di voce prezzo applicata a un preventivo.
 */
public enum PricingLineType {

    BASE_FREIGHT(false, false),
    DISTANCE_CHARGE(false, false),
    FUEL_SURCHARGE(true, false),
    TOLL_CHARGE(true, false),
    VEHICLE_WEAR_CHARGE(true, false),
    ADR_SURCHARGE(true, false),
    TEMPERATURE_CONTROL_SURCHARGE(true, false),
    WAITING_TIME_CHARGE(true, false),
    HANDLING_CHARGE(true, false),
    DISCOUNT(false, true);

    private final boolean surcharge;
    private final boolean discount;

    PricingLineType(boolean surcharge, boolean discount) {
        this.surcharge = surcharge;
        this.discount = discount;
    }

    public boolean isSurcharge() {
        return surcharge;
    }

    public boolean isDiscount() {
        return discount;
    }

    public boolean increasesTotal() {
        return !discount;
    }

    public boolean decreasesTotal() {
        return discount;
    }
}
