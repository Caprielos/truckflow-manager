package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.MarkNotEligibleMechanicCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.MarkNotEligibleMechanicUseCase;
import it.gabriele.truckflow.application.port.out.operational.MechanicRepository;
import it.gabriele.truckflow.application.result.operational.MechanicResult;

/** Application service that changes the status of operational mechanic roles. */
public final class MarkNotEligibleMechanicService implements MarkNotEligibleMechanicUseCase {

  private final MechanicRepository mechanicRepository;

  public MarkNotEligibleMechanicService(MechanicRepository mechanicRepository) {
    UseCaseValidationException.requireNonNull(mechanicRepository, "mechanicRepository");
    this.mechanicRepository = mechanicRepository;
  }

  @Override
  public MechanicResult execute(MarkNotEligibleMechanicCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var existingMechanic =
        mechanicRepository
            .findById(command.id())
            .orElseThrow(() -> new ResourceNotFoundException("Mechanic", command.id()));
    var updatedMechanic = MechanicMutationSupport.copyOf(existingMechanic);
    updatedMechanic.markNotEligible(command.updatedBy());

    return MechanicResult.from(mechanicRepository.save(updatedMechanic));
  }
}
