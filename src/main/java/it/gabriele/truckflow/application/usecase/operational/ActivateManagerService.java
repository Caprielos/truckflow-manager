package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.ActivateManagerCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.ActivateManagerUseCase;
import it.gabriele.truckflow.application.port.out.operational.ManagerRepository;
import it.gabriele.truckflow.application.result.operational.ManagerResult;

/** Application service that changes the status of operational manager roles. */
public final class ActivateManagerService implements ActivateManagerUseCase {

  private final ManagerRepository managerRepository;

  public ActivateManagerService(ManagerRepository managerRepository) {
    UseCaseValidationException.requireNonNull(managerRepository, "managerRepository");
    this.managerRepository = managerRepository;
  }

  @Override
  public ManagerResult execute(ActivateManagerCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var existingManager =
        managerRepository
            .findById(command.id())
            .orElseThrow(() -> new ResourceNotFoundException("Manager", command.id()));
    var updatedManager = ManagerMutationSupport.copyOf(existingManager);
    updatedManager.activate(command.updatedBy());

    return ManagerResult.from(managerRepository.save(updatedManager));
  }
}
