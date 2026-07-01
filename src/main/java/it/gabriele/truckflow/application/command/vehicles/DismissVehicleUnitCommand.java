package it.gabriele.truckflow.application.command.vehicles;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;

/** Command used to dismiss an existing vehicle unit from the active fleet registry. */
public record DismissVehicleUnitCommand(VehicleUnitId vehicleUnitId) implements ApplicationCommand {

  public DismissVehicleUnitCommand {
    UseCaseValidationException.requireNonNull(vehicleUnitId, "vehicleUnitId");
  }
}
