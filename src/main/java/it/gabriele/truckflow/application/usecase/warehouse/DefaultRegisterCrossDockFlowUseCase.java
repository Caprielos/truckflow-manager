package it.gabriele.truckflow.application.usecase.warehouse;

import it.gabriele.truckflow.application.port.in.warehouse.RegisterCrossDockFlowUseCase;
import it.gabriele.truckflow.application.port.out.warehouse.CrossDockFlowRepository;
import it.gabriele.truckflow.domain.warehouse.CrossDockFlow;
import java.util.Objects;

/** Implementazione default di RegisterCrossDockFlowUseCase. */
public final class DefaultRegisterCrossDockFlowUseCase implements RegisterCrossDockFlowUseCase {

  private final CrossDockFlowRepository repository;

  public DefaultRegisterCrossDockFlowUseCase(CrossDockFlowRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public CrossDockFlow handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    CrossDockFlow aggregate =
        Objects.requireNonNull(command.flow(), "Il flusso cross-dock è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
