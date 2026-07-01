package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.driver.DriverId;

/** Command used to markNotEligible an operational driver role. */
public record MarkNotEligibleDriverCommand(DriverId id, String updatedBy)
    implements ApplicationCommand {

  public MarkNotEligibleDriverCommand {
    UseCaseValidationException.requireNonNull(id, "id");
    UseCaseValidationException.requireNotBlank(updatedBy, "updatedBy");
  }
}
