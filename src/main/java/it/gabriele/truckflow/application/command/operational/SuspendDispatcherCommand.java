package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.dispatcher.DispatcherId;

/** Command used to suspend an operational dispatcher role. */
public record SuspendDispatcherCommand(DispatcherId id, String updatedBy)
    implements ApplicationCommand {

  public SuspendDispatcherCommand {
    UseCaseValidationException.requireNonNull(id, "id");
    UseCaseValidationException.requireNotBlank(updatedBy, "updatedBy");
  }
}
