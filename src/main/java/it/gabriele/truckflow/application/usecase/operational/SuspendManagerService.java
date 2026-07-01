package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.SuspendManagerCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.SuspendManagerUseCase;
import it.gabriele.truckflow.application.port.out.operational.ManagerRepository;
import it.gabriele.truckflow.application.result.operational.ManagerResult;

/** Application service that changes the status of operational manager roles. */
public final class SuspendManagerService implements SuspendManagerUseCase {

  private final ManagerRepository managerRepository;

  public SuspendManagerService(ManagerRepository managerRepository) {
    UseCaseValidationException.requireNonNull(managerRepository, "managerRepository");
    this.managerRepository = managerRepository;
  }

  @Override
  public ManagerResult execute(SuspendManagerCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var existingManager =
        managerRepository
            .findById(command.id())
            .orElseThrow(() -> new ResourceNotFoundException("Manager", command.id()));
    var updatedManager = ManagerMutationSupport.copyOf(existingManager);
    updatedManager.suspend(command.updatedBy());

    return ManagerResult.from(managerRepository.save(updatedManager));
  }
}
