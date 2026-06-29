package it.gabriele.truckflow.domain.fleet;

/** Unità fisica del mezzo: camion, trattore, rimorchio o semirimorchio. */
public enum VehicleUnitType {
  VAN(true, true, false),
  RIGID_TRUCK(true, true, false),
  TRACTOR_UNIT(true, false, false),
  DRAWBAR_TRAILER(false, true, true),
  CENTER_AXLE_TRAILER(false, true, true),
  SEMI_TRAILER(false, true, true);

  private final boolean poweredUnit;
  private final boolean cargoCapable;
  private final boolean towedUnit;

  VehicleUnitType(boolean poweredUnit, boolean cargoCapable, boolean towedUnit) {
    this.poweredUnit = poweredUnit;
    this.cargoCapable = cargoCapable;
    this.towedUnit = towedUnit;
  }

  public boolean isPoweredUnit() {
    return poweredUnit;
  }

  public boolean canCarryCargo() {
    return cargoCapable;
  }

  public boolean isTowedUnit() {
    return towedUnit;
  }

  public boolean isTrailer() {
    return this == DRAWBAR_TRAILER || this == CENTER_AXLE_TRAILER;
  }

  public boolean isSemiTrailer() {
    return this == SEMI_TRAILER;
  }
}
