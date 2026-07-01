package it.gabriele.truckflow.application.command.operational;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperatorId;

/** Command used to markNotEligible an operational warehouseoperator role. */
public record MarkNotEligibleWarehouseOperatorCommand(WarehouseOperatorId id, String updatedBy)
    implements ApplicationCommand {

  public MarkNotEligibleWarehouseOperatorCommand {
    UseCaseValidationException.requireNonNull(id, "id");
    UseCaseValidationException.requireNotBlank(updatedBy, "updatedBy");
  }
}
