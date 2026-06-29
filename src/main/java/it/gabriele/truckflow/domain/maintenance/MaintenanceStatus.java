package it.gabriele.truckflow.domain.maintenance;

/** Stato di un ordine di manutenzione. */
public enum MaintenanceStatus {
  OPEN(false, false),
  SCHEDULED(false, true),
  IN_PROGRESS(false, true),
  COMPLETED(true, false),
  CANCELLED(true, false);

  private final boolean terminal;
  private final boolean blockingVehicleAvailability;

  MaintenanceStatus(boolean terminal, boolean blockingVehicleAvailability) {
    this.terminal = terminal;
    this.blockingVehicleAvailability = blockingVehicleAvailability;
  }

  public boolean isTerminal() {
    return terminal;
  }

  public boolean blocksVehicleAvailability() {
    return blockingVehicleAvailability;
  }
}
