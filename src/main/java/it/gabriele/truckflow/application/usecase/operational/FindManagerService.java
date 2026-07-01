package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.FindManagerCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.FindManagerUseCase;
import it.gabriele.truckflow.application.port.out.operational.ManagerRepository;
import it.gabriele.truckflow.application.result.operational.ManagerResult;

/** Application service that finds operational manager roles. */
public final class FindManagerService implements FindManagerUseCase {

  private final ManagerRepository managerRepository;

  public FindManagerService(ManagerRepository managerRepository) {
    UseCaseValidationException.requireNonNull(managerRepository, "managerRepository");
    this.managerRepository = managerRepository;
  }

  @Override
  public ManagerResult execute(FindManagerCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    return managerRepository
        .findById(command.id())
        .map(ManagerResult::from)
        .orElseThrow(() -> new ResourceNotFoundException("Manager", command.id()));
  }
}
