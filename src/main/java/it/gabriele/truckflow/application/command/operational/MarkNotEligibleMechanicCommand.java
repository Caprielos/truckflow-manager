package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.mechanic.MechanicId;

/** Command used to mark an operational mechanic role as not eligible. */
public record MarkNotEligibleMechanicCommand(MechanicId id, String updatedBy)
    implements ApplicationCommand {

  public MarkNotEligibleMechanicCommand {
    UseCaseValidationException.requireNonNull(id, "id");
    UseCaseValidationException.requireNotBlank(updatedBy, "updatedBy");
  }
}
