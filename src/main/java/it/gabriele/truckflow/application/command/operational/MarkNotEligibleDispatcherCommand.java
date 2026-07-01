package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.dispatcher.DispatcherId;

/** Command used to mark an operational dispatcher role as not eligible. */
public record MarkNotEligibleDispatcherCommand(DispatcherId id, String updatedBy)
    implements ApplicationCommand {

  public MarkNotEligibleDispatcherCommand {
    UseCaseValidationException.requireNonNull(id, "id");
    UseCaseValidationException.requireNotBlank(updatedBy, "updatedBy");
  }
}
