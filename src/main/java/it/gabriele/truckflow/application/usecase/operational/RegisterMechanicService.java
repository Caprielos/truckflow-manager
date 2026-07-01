package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.RegisterMechanicCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.RegisterMechanicUseCase;
import it.gabriele.truckflow.application.port.out.operational.MechanicRepository;
import it.gabriele.truckflow.application.result.operational.MechanicResult;
import it.gabriele.truckflow.domain.operational.mechanic.Mechanic;

/** Application service that registers operational mechanic roles. */
public final class RegisterMechanicService implements RegisterMechanicUseCase {

  private final MechanicRepository mechanicRepository;

  public RegisterMechanicService(MechanicRepository mechanicRepository) {
    UseCaseValidationException.requireNonNull(mechanicRepository, "mechanicRepository");
    this.mechanicRepository = mechanicRepository;
  }

  @Override
  public MechanicResult execute(RegisterMechanicCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    if (mechanicRepository.existsByCode(command.code())) {
      throw new DuplicateResourceException("Mechanic", command.code().value());
    }

    if (mechanicRepository.existsByUserId(command.userId())) {
      throw new DuplicateResourceException("Mechanic", command.userId().value());
    }

    var mechanic =
        new Mechanic(
            null,
            command.code(),
            command.userId(),
            command.profile(),
            command.qualifications(),
            command.status(),
            command.metadata(),
            command.notes());

    return MechanicResult.from(mechanicRepository.save(mechanic));
  }
}
