package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.RegisterManagerCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.RegisterManagerUseCase;
import it.gabriele.truckflow.application.port.out.operational.ManagerRepository;
import it.gabriele.truckflow.application.result.operational.ManagerResult;
import it.gabriele.truckflow.domain.operational.manager.Manager;

/** Application service that registers operational manager roles. */
public final class RegisterManagerService implements RegisterManagerUseCase {

  private final ManagerRepository managerRepository;

  public RegisterManagerService(ManagerRepository managerRepository) {
    UseCaseValidationException.requireNonNull(managerRepository, "managerRepository");
    this.managerRepository = managerRepository;
  }

  @Override
  public ManagerResult execute(RegisterManagerCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    if (managerRepository.existsByCode(command.code())) {
      throw new DuplicateResourceException("Manager", command.code().value());
    }

    if (managerRepository.existsByUserId(command.userId())) {
      throw new DuplicateResourceException("Manager", command.userId().value());
    }

    var manager =
        new Manager(
            null,
            command.code(),
            command.userId(),
            command.profile(),
            command.scopes(),
            command.status(),
            command.metadata(),
            command.notes());

    return ManagerResult.from(managerRepository.save(manager));
  }
}
