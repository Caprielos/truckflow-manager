package it.gabriele.truckflow.domain.contract;

/**
 * Tipo reale di regola tariffaria usata nei contratti cliente.
 */
public enum TariffRuleType {

    BASE_TRANSPORT_FEE(false),
    FUEL_SURCHARGE(true),
    TOLL_PASS_THROUGH(true),
    ADR_SURCHARGE(true),
    REFRIGERATED_SURCHARGE(true),
    PHARMACEUTICAL_SURCHARGE(true),
    FOOD_GRADE_SURCHARGE(true),
    LIVE_ANIMAL_SURCHARGE(true),
    WASTE_SURCHARGE(true),
    OVERSIZED_TRANSPORT_SURCHARGE(true),
    EXPRESS_SURCHARGE(true),
    NIGHT_SURCHARGE(true),
    HOLIDAY_SURCHARGE(true),
    INTERNATIONAL_SURCHARGE(true),
    WAITING_TIME_CHARGE(true),
    LOADING_UNLOADING_CHARGE(true),
    PALLET_EXCHANGE_CHARGE(true),
    STORAGE_CHARGE(true),
    RETURN_LOAD_DISCOUNT(false),
    OTHER(true);

    private final boolean operationalCircumstance;

    TariffRuleType(boolean operationalCircumstance) {
        this.operationalCircumstance = operationalCircumstance;
    }

    public boolean isOperationalCircumstance() {
        return operationalCircumstance;
    }
}
