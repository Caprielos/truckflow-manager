package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.dispatcher.DispatcherId;

/** Command used to find an operational dispatcher role. */
public record FindDispatcherCommand(DispatcherId id) implements ApplicationCommand {

  public FindDispatcherCommand {
    UseCaseValidationException.requireNonNull(id, "id");
  }
}
