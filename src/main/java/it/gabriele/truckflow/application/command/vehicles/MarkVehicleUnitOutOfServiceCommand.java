package it.gabriele.truckflow.application.command.vehicles;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.vehicles.unit.VehicleUnitId;

/** Command used to mark an existing vehicle unit out of service. */
public record MarkVehicleUnitOutOfServiceCommand(VehicleUnitId vehicleUnitId)
    implements ApplicationCommand {

  public MarkVehicleUnitOutOfServiceCommand {
    UseCaseValidationException.requireNonNull(vehicleUnitId, "vehicleUnitId");
  }
}
