package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.MarkNotEligibleManagerCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.MarkNotEligibleManagerUseCase;
import it.gabriele.truckflow.application.port.out.operational.ManagerRepository;
import it.gabriele.truckflow.application.result.operational.ManagerResult;

/** Application service that changes the status of operational manager roles. */
public final class MarkNotEligibleManagerService implements MarkNotEligibleManagerUseCase {

  private final ManagerRepository managerRepository;

  public MarkNotEligibleManagerService(ManagerRepository managerRepository) {
    UseCaseValidationException.requireNonNull(managerRepository, "managerRepository");
    this.managerRepository = managerRepository;
  }

  @Override
  public ManagerResult execute(MarkNotEligibleManagerCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var existingManager =
        managerRepository
            .findById(command.id())
            .orElseThrow(() -> new ResourceNotFoundException("Manager", command.id()));
    var updatedManager = ManagerMutationSupport.copyOf(existingManager);
    updatedManager.markNotEligible(command.updatedBy());

    return ManagerResult.from(managerRepository.save(updatedManager));
  }
}
