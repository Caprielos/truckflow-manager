package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.mechanic.MechanicId;

/** Command used to suspend an operational mechanic role. */
public record SuspendMechanicCommand(MechanicId id, String updatedBy)
    implements ApplicationCommand {

  public SuspendMechanicCommand {
    UseCaseValidationException.requireNonNull(id, "id");
    UseCaseValidationException.requireNotBlank(updatedBy, "updatedBy");
  }
}
