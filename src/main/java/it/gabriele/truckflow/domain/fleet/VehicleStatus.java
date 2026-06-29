package it.gabriele.truckflow.domain.fleet;

/** Rappresenta lo stato operativo di un veicolo. */
public enum VehicleStatus {
  AVAILABLE(true),
  ASSIGNED(false),
  IN_MAINTENANCE(false),
  OUT_OF_SERVICE(false),
  RETIRED(false);

  private final boolean assignable;

  VehicleStatus(boolean assignable) {
    this.assignable = assignable;
  }

  public boolean canBeAssigned() {
    return assignable;
  }
}
