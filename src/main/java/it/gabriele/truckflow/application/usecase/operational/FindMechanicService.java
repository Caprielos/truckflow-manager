package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.FindMechanicCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.FindMechanicUseCase;
import it.gabriele.truckflow.application.port.out.operational.MechanicRepository;
import it.gabriele.truckflow.application.result.operational.MechanicResult;

/** Application service that finds operational mechanic roles. */
public final class FindMechanicService implements FindMechanicUseCase {

  private final MechanicRepository mechanicRepository;

  public FindMechanicService(MechanicRepository mechanicRepository) {
    UseCaseValidationException.requireNonNull(mechanicRepository, "mechanicRepository");
    this.mechanicRepository = mechanicRepository;
  }

  @Override
  public MechanicResult execute(FindMechanicCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    return mechanicRepository
        .findById(command.id())
        .map(MechanicResult::from)
        .orElseThrow(() -> new ResourceNotFoundException("Mechanic", command.id()));
  }
}
