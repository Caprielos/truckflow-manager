package it.gabriele.truckflow.domain.economics;

/**
 * Categoria di acquisto da fattura fornitore.
 */
public enum PurchaseCategory {
    VEHICLE_PURCHASE(true),
    TRAILER_PURCHASE(true),
    BODY_EQUIPMENT_PURCHASE(true),
    LOADING_EQUIPMENT_PURCHASE(true),
    TIRE_PURCHASE(false),
    FUEL(false),
    TOLL(false),
    INSURANCE_PREMIUM(false),
    MAINTENANCE_PARTS(false),
    MAINTENANCE_LABOR(false),
    ROAD_TAX(false),
    TELEMATICS_SUBSCRIPTION(false),
    WASHING_SANITATION(false),
    PARKING(false),
    FERRY_OR_TRAIN(false),
    OUTSOURCED_TRANSPORT(false),
    OFFICE_OR_ADMIN(false),
    OTHER(false);

    private final boolean capitalAsset;

    PurchaseCategory(boolean capitalAsset) {
        this.capitalAsset = capitalAsset;
    }

    public boolean isCapitalAsset() {
        return capitalAsset;
    }

    public boolean isOperatingExpense() {
        return !capitalAsset;
    }
}
