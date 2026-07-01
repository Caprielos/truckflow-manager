package it.gabriele.truckflow.application.command.vehicles;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombinationType;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleStatus;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;

/** Command used to register an operational vehicle combination from existing vehicle units. */
public record RegisterVehicleCombinationCommand(
    VehicleCombinationType combinationType,
    VehicleUnitId primaryUnitId,
    VehicleUnitId secondaryUnitId,
    VehicleStatus status,
    String notes)
    implements ApplicationCommand {

  public RegisterVehicleCombinationCommand {
    UseCaseValidationException.requireNonNull(combinationType, "combinationType");
    UseCaseValidationException.requireNonNull(primaryUnitId, "primaryUnitId");
    UseCaseValidationException.requireNonNull(status, "status");
  }
}
