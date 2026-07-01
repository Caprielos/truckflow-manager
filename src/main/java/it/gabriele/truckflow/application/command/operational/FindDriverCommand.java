package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.driver.DriverId;

/** Command used to find an operational driver role. */
public record FindDriverCommand(DriverId id) implements ApplicationCommand {

  public FindDriverCommand {
    UseCaseValidationException.requireNonNull(id, "id");
  }
}
