package it.gabriele.truckflow.application.command.vehicles;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombinationId;

/** Command used to find an existing vehicle combination by identity. */
public record FindVehicleCombinationCommand(VehicleCombinationId vehicleCombinationId)
    implements ApplicationCommand {

  public FindVehicleCombinationCommand {
    UseCaseValidationException.requireNonNull(vehicleCombinationId, "vehicleCombinationId");
  }
}
