package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.manager.ManagerId;

/** Command used to mark an operational manager role as not eligible. */
public record MarkNotEligibleManagerCommand(ManagerId id, String updatedBy)
    implements ApplicationCommand {

  public MarkNotEligibleManagerCommand {
    UseCaseValidationException.requireNonNull(id, "id");
    UseCaseValidationException.requireNotBlank(updatedBy, "updatedBy");
  }
}
