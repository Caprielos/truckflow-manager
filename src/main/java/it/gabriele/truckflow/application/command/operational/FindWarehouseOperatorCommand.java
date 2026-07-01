package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperatorId;

/** Command used to find an operational warehouseoperator role. */
public record FindWarehouseOperatorCommand(WarehouseOperatorId id) implements ApplicationCommand {

  public FindWarehouseOperatorCommand {
    UseCaseValidationException.requireNonNull(id, "id");
  }
}
