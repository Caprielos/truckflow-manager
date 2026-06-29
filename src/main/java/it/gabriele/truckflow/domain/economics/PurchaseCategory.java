package it.gabriele.truckflow.domain.economics;

/**
 * Categoria di acquisto da fattura fornitore. Le categorie coprono beni capitalizzabili, costi
 * operativi, servizi, tasse e qualunque spesa tracciabile.
 */
public enum PurchaseCategory {
  VEHICLE_PURCHASE(true),
  TRACTOR_UNIT_PURCHASE(true),
  RIGID_TRUCK_PURCHASE(true),
  VAN_PURCHASE(true),
  TRAILER_PURCHASE(true),
  SEMI_TRAILER_PURCHASE(true),
  DRAWBAR_TRAILER_PURCHASE(true),
  CENTER_AXLE_TRAILER_PURCHASE(true),
  BODY_EQUIPMENT_PURCHASE(true),
  REFRIGERATION_UNIT_PURCHASE(true),
  LOADING_EQUIPMENT_PURCHASE(true),
  CRANE_PURCHASE(true),
  TELEMATICS_DEVICE_PURCHASE(true),
  WORKSHOP_EQUIPMENT_PURCHASE(true),
  WAREHOUSE_EQUIPMENT_PURCHASE(true),
  SOFTWARE_LICENSE_PURCHASE(true),
  TIRE_PURCHASE(false),
  FUEL(false),
  ADD_BLUE(false),
  LUBRICANT(false),
  TOLL(false),
  INSURANCE_PREMIUM(false),
  INSURANCE_DEDUCTIBLE(false),
  MAINTENANCE_PARTS(false),
  MAINTENANCE_LABOR(false),
  BODY_REPAIR(false),
  TIRE_SERVICE(false),
  ROAD_TAX(false),
  VEHICLE_INSPECTION(false),
  TACHOGRAPH_CALIBRATION(false),
  TELEMATICS_SUBSCRIPTION(false),
  SOFTWARE_SUBSCRIPTION(false),
  WASHING_SANITATION(false),
  PARKING(false),
  FERRY_OR_TRAIN(false),
  OUTSOURCED_TRANSPORT(false),
  LOADING_UNLOADING_SERVICE(false),
  WAREHOUSE_STORAGE(false),
  PERMIT_OR_LICENSE(false),
  DRIVER_TRAINING(false),
  DRIVER_MEDICAL_CHECK(false),
  DRIVER_ALLOWANCE(false),
  OFFICE_OR_ADMIN(false),
  ACCOUNTING_SERVICE(false),
  BANK_FEE(false),
  FINANCING_INTEREST(false),
  LEASING_RENTAL(false),
  RENTAL_VEHICLE(false),
  CUSTOMS_OR_IMPORT_DUTY(false),
  REGISTRATION_COST(false),
  LEGAL_SERVICE(false),
  CLAIM_COST(false),
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
