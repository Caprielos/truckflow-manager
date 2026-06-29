package it.gabriele.truckflow.application.usecase.warehouse;

import it.gabriele.truckflow.application.port.in.RegisterWarehouseLocationUseCase;
import it.gabriele.truckflow.application.port.out.EnterpriseWarehouseLocationRepository;
import it.gabriele.truckflow.domain.warehouse.WarehouseLocation;
import java.util.Objects;

/** Implementazione default di RegisterWarehouseLocationUseCase. */
public final class DefaultRegisterWarehouseLocationUseCase
    implements RegisterWarehouseLocationUseCase {

  private final EnterpriseWarehouseLocationRepository repository;

  public DefaultRegisterWarehouseLocationUseCase(EnterpriseWarehouseLocationRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public WarehouseLocation handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    WarehouseLocation aggregate =
        Objects.requireNonNull(command.location(), "L ubicazione warehouse è obbligatoria.");
    repository.save(aggregate);
    return aggregate;
  }
}
