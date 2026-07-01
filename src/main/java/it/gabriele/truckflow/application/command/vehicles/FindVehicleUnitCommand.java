package it.gabriele.truckflow.application.command.vehicles;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;

/** Command used to find an existing vehicle unit by identity. */
public record FindVehicleUnitCommand(VehicleUnitId vehicleUnitId) implements ApplicationCommand {

  public FindVehicleUnitCommand {
    UseCaseValidationException.requireNonNull(vehicleUnitId, "vehicleUnitId");
  }
}
