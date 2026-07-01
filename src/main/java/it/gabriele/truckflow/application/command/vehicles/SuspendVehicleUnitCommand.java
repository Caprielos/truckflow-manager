package it.gabriele.truckflow.application.command.vehicles;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;

/** Command used to suspend an existing vehicle unit. */
public record SuspendVehicleUnitCommand(VehicleUnitId vehicleUnitId) implements ApplicationCommand {

  public SuspendVehicleUnitCommand {
    UseCaseValidationException.requireNonNull(vehicleUnitId, "vehicleUnitId");
  }
}
