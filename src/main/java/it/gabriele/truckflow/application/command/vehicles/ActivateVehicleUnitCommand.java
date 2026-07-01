package it.gabriele.truckflow.application.command.vehicles;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;

/** Command used to activate an existing vehicle unit. */
public record ActivateVehicleUnitCommand(VehicleUnitId vehicleUnitId)
    implements ApplicationCommand {

  public ActivateVehicleUnitCommand {
    UseCaseValidationException.requireNonNull(vehicleUnitId, "vehicleUnitId");
  }
}
