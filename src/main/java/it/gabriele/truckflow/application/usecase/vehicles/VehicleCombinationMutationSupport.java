package it.gabriele.truckflow.application.usecase.vehicles;

import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombination;

/** Utility used by vehicle combination use cases to mutate copies before saving. */
final class VehicleCombinationMutationSupport {

  private VehicleCombinationMutationSupport() {}

  static VehicleCombination copyOf(VehicleCombination vehicleCombination) {
    return new VehicleCombination(
        vehicleCombination.id(),
        vehicleCombination.combinationType(),
        vehicleCombination.primaryUnitId(),
        vehicleCombination.secondaryUnitId(),
        vehicleCombination.combinedCapabilities(),
        vehicleCombination.operationalRoles(),
        vehicleCombination.status(),
        vehicleCombination.notes());
  }
}
