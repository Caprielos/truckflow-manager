package it.gabriele.truckflow.domain.fleet;

/**
 * Tipo fisico del veicolo.
 *
 * <p>Manteniamo il nome VehicleType per compatibilità con il codice esistente, ma il significato
 * corretto è VehicleUnitType: non descrive più il tipo di carico, descrive l'unità fisica della
 * flotta.
 */
public enum VehicleType {
  VAN(VehicleUnitType.VAN),
  RIGID_TRUCK(VehicleUnitType.RIGID_TRUCK),
  TRACTOR_UNIT(VehicleUnitType.TRACTOR_UNIT),
  DRAWBAR_TRAILER(VehicleUnitType.DRAWBAR_TRAILER),
  CENTER_AXLE_TRAILER(VehicleUnitType.CENTER_AXLE_TRAILER),
  SEMI_TRAILER(VehicleUnitType.SEMI_TRAILER),

  /**
   * @deprecated usare RIGID_TRUCK + VehicleBodyBaseType.REFRIGERATED_BOX.
   */
  @Deprecated
  REFRIGERATED_TRUCK(VehicleUnitType.RIGID_TRUCK, true),

  /**
   * @deprecated usare SEMI_TRAILER + VehicleBodyBaseType.REFRIGERATED_BOX.
   */
  @Deprecated
  REFRIGERATED_TRAILER(VehicleUnitType.SEMI_TRAILER, true);

  private final VehicleUnitType unitType;
  private final boolean legacyTemperatureControlledType;

  VehicleType(VehicleUnitType unitType) {
    this(unitType, false);
  }

  VehicleType(VehicleUnitType unitType, boolean legacyTemperatureControlledType) {
    this.unitType = unitType;
    this.legacyTemperatureControlledType = legacyTemperatureControlledType;
  }

  public VehicleUnitType getUnitType() {
    return unitType;
  }

  public boolean canCarryCargo() {
    return unitType.canCarryCargo();
  }

  public boolean supportsTemperatureControl() {
    return legacyTemperatureControlledType;
  }

  public boolean isPoweredUnit() {
    return unitType.isPoweredUnit();
  }

  public boolean isTrailer() {
    return unitType.isTowedUnit();
  }

  public boolean isSemiTrailer() {
    return unitType == VehicleUnitType.SEMI_TRAILER;
  }

  public boolean isDrawbarTrailer() {
    return unitType == VehicleUnitType.DRAWBAR_TRAILER
        || unitType == VehicleUnitType.CENTER_AXLE_TRAILER;
  }
}
