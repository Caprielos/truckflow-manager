package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperatorId;

/** Command used to activate an operational warehouse operator role. */
public record ActivateWarehouseOperatorCommand(WarehouseOperatorId id, String updatedBy)
    implements ApplicationCommand {

  public ActivateWarehouseOperatorCommand {
    UseCaseValidationException.requireNonNull(id, "id");
    UseCaseValidationException.requireNotBlank(updatedBy, "updatedBy");
  }
}
