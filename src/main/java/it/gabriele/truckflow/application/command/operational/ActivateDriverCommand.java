package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.driver.DriverId;

/** Command used to activate an operational driver role. */
public record ActivateDriverCommand(DriverId id, String updatedBy) implements ApplicationCommand {

  public ActivateDriverCommand {
    UseCaseValidationException.requireNonNull(id, "id");
    UseCaseValidationException.requireNotBlank(updatedBy, "updatedBy");
  }
}
