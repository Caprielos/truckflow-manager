package it.gabriele.truckflow.application.result.vehicles;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.result.ApplicationResult;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombination;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombinationId;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombinationType;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleStatus;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;

/** Result returned by vehicle combination use cases. */
public record VehicleCombinationResult(
    VehicleCombinationId id,
    VehicleCombinationType combinationType,
    VehicleUnitId primaryUnitId,
    VehicleUnitId secondaryUnitId,
    boolean hasSecondaryUnit,
    VehicleStatus status,
    int capabilityCount,
    int operationalRoleCount)
    implements ApplicationResult {

  public static VehicleCombinationResult from(VehicleCombination vehicleCombination) {
    UseCaseValidationException.requireNonNull(vehicleCombination, "vehicleCombination");

    return new VehicleCombinationResult(
        vehicleCombination.id(),
        vehicleCombination.combinationType(),
        vehicleCombination.primaryUnitId(),
        vehicleCombination.secondaryUnitId(),
        vehicleCombination.hasSecondaryUnit(),
        vehicleCombination.status(),
        vehicleCombination.combinedCapabilities().size(),
        vehicleCombination.operationalRoles().size());
  }
}
