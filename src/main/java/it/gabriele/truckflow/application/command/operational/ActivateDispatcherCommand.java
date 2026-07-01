package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.dispatcher.DispatcherId;

/** Command used to activate an operational dispatcher role. */
public record ActivateDispatcherCommand(DispatcherId id, String updatedBy)
    implements ApplicationCommand {

  public ActivateDispatcherCommand {
    UseCaseValidationException.requireNonNull(id, "id");
    UseCaseValidationException.requireNotBlank(updatedBy, "updatedBy");
  }
}
