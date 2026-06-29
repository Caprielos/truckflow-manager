package it.gabriele.truckflow.domain.fleet;

/**
 * Allestimento legacy usato dal modello attuale. Per i nuovi sviluppi usare
 * VehicleBodyConfiguration + VehicleBodyBaseType.
 */
public enum VehicleBodyType {
  NONE(false, false, false, false, false, false),

  VAN_BODY(true, false, false, false, false, false),
  BOX(true, false, false, false, false, false),
  DRY_BOX(true, false, false, false, false, false),
  CURTAIN_SIDE(true, false, false, false, false, false),
  ISOTHERMAL_BOX(true, true, false, false, false, false),
  REFRIGERATED_BOX(true, true, false, false, false, false),

  FIXED_OPEN_BOX(true, false, false, false, false, true),
  FLATBED(true, false, false, false, false, true),
  FLATBED_WITH_RAMPS(true, false, false, false, false, true),
  LOW_LOADER(true, false, false, false, false, true),
  EXTENDABLE_FLATBED(true, false, false, false, false, true),
  CONTAINER_CHASSIS(true, false, false, false, false, true),
  SWAP_BODY_CARRIER(true, false, false, false, false, true),
  HOOKLIFT_CHASSIS(true, false, false, false, false, true),

  TIPPER(true, false, false, false, true, true),
  REAR_TIPPER(true, false, false, false, true, true),
  THREE_WAY_TIPPER(true, false, false, false, true, true),
  WALKING_FLOOR(true, false, false, false, true, false),
  SILO(true, false, false, false, true, false),

  TANK_LIQUID(true, false, true, true, false, false),
  TANK_FUEL(true, false, true, true, false, false),
  TANK_GAS(true, false, true, false, false, false),
  FOOD_GRADE_TANK(true, false, true, true, false, false),

  CAR_TRANSPORTER(true, false, false, false, false, true),
  LIVESTOCK(true, false, false, false, false, false),
  COIL_CARRIER(true, false, false, false, false, false),
  CONCRETE_MIXER(true, false, false, false, true, false);

  private final boolean cargoBody;
  private final boolean temperatureControlled;
  private final boolean tank;
  private final boolean liquidTank;
  private final boolean bulkDryBody;
  private final boolean openBody;

  VehicleBodyType(
      boolean cargoBody,
      boolean temperatureControlled,
      boolean tank,
      boolean liquidTank,
      boolean bulkDryBody,
      boolean openBody) {
    this.cargoBody = cargoBody;
    this.temperatureControlled = temperatureControlled;
    this.tank = tank;
    this.liquidTank = liquidTank;
    this.bulkDryBody = bulkDryBody;
    this.openBody = openBody;
  }

  public boolean isCargoBody() {
    return cargoBody;
  }

  public boolean supportsTemperatureControl() {
    return temperatureControlled;
  }

  public boolean isTank() {
    return tank;
  }

  public boolean isLiquidTank() {
    return liquidTank;
  }

  public boolean isGasTank() {
    return this == TANK_GAS;
  }

  public boolean isFuelTank() {
    return this == TANK_FUEL;
  }

  public boolean isBulkDryBody() {
    return bulkDryBody;
  }

  public boolean isOpenBody() {
    return openBody;
  }
}
