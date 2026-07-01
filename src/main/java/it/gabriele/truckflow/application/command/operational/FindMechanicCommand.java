package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.mechanic.MechanicId;

/** Command used to find an operational mechanic role. */
public record FindMechanicCommand(MechanicId id) implements ApplicationCommand {

  public FindMechanicCommand {
    UseCaseValidationException.requireNonNull(id, "id");
  }
}
