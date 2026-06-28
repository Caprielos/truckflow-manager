package it.gabriele.truckflow.domain.economics;

/**
 * Costi diretti o allocati per eseguire una missione di trasporto.
 */
public enum MissionCostType {
    FUEL,
    TOLL,
    DRIVER_WAGE,
    DRIVER_ALLOWANCE,
    VEHICLE_DEPRECIATION,
    TRAILER_DEPRECIATION,
    BODY_EQUIPMENT_DEPRECIATION,
    TIRE_WEAR,
    MAINTENANCE_RESERVE,
    INSURANCE_QUOTA,
    ROAD_TAX_QUOTA,
    PARKING,
    FERRY_OR_TRAIN,
    WASHING_SANITATION,
    LOADING_UNLOADING,
    OUTSOURCED_CARRIER,
    CLAIM_DEDUCTIBLE,
    OTHER;

    public boolean isVariableOperationalCost() {
        return switch (this) {
            case FUEL, TOLL, DRIVER_ALLOWANCE, PARKING, FERRY_OR_TRAIN, WASHING_SANITATION,
                 LOADING_UNLOADING, OUTSOURCED_CARRIER, CLAIM_DEDUCTIBLE -> true;
            default -> false;
        };
    }
}
