package it.gabriele.truckflow.application.usecase.warehouse;

import it.gabriele.truckflow.application.port.in.warehouse.RegisterLoadingDockUseCase;
import it.gabriele.truckflow.application.port.out.LoadingDockRepository;
import it.gabriele.truckflow.domain.warehouse.LoadingDock;
import java.util.Objects;

/** Implementazione default di RegisterLoadingDockUseCase. */
public final class DefaultRegisterLoadingDockUseCase implements RegisterLoadingDockUseCase {

  private final LoadingDockRepository repository;

  public DefaultRegisterLoadingDockUseCase(LoadingDockRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public LoadingDock handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    LoadingDock aggregate =
        Objects.requireNonNull(command.dock(), "La baia di carico è obbligatoria.");
    repository.save(aggregate);
    return aggregate;
  }
}
