package it.gabriele.truckflow.domain.parking;

/** Tipo di risorsa che può occupare un posto parcheggio. */
public enum ParkingResourceType {
  VAN,
  RIGID_TRUCK,
  TRACTOR_UNIT,
  TRAILER,
  SEMI_TRAILER,
  ARTICULATED_VEHICLE,
  TRUCK_AND_TRAILER,
  EQUIPMENT,
  OTHER;

  public boolean isCombination() {
    return this == ARTICULATED_VEHICLE || this == TRUCK_AND_TRAILER;
  }

  public boolean isTowedUnit() {
    return this == TRAILER || this == SEMI_TRAILER;
  }

  public boolean isPoweredSingleUnit() {
    return this == VAN || this == RIGID_TRUCK || this == TRACTOR_UNIT;
  }
}
