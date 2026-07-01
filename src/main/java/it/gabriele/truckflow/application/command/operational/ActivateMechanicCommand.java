package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.mechanic.MechanicId;

/** Command used to activate an operational mechanic role. */
public record ActivateMechanicCommand(MechanicId id, String updatedBy)
    implements ApplicationCommand {

  public ActivateMechanicCommand {
    UseCaseValidationException.requireNonNull(id, "id");
    UseCaseValidationException.requireNotBlank(updatedBy, "updatedBy");
  }
}
