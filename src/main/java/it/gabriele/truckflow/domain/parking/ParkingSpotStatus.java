package it.gabriele.truckflow.domain.parking;

/** Stato operativo di un posto parcheggio. */
public enum ParkingSpotStatus {
  AVAILABLE,
  OCCUPIED,
  RESERVED,
  OUT_OF_SERVICE;

  public boolean canReceiveNewAssignment() {
    return this == AVAILABLE;
  }
}
