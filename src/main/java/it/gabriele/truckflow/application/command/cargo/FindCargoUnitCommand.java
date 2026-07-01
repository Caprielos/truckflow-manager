package it.gabriele.truckflow.application.command.cargo;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.cargo.CargoId;

/** Command used to find a cargo unit by identifier. */
public record FindCargoUnitCommand(CargoId cargoId) implements ApplicationCommand {

  public FindCargoUnitCommand {
    UseCaseValidationException.requireNonNull(cargoId, "cargoId");
  }
}
