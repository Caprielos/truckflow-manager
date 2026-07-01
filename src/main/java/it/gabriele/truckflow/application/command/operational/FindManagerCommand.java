package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.manager.ManagerId;

/** Command used to find an operational manager role. */
public record FindManagerCommand(ManagerId id) implements ApplicationCommand {

  public FindManagerCommand {
    UseCaseValidationException.requireNonNull(id, "id");
  }
}
