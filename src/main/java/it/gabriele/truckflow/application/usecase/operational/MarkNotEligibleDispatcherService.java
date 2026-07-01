package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.MarkNotEligibleDispatcherCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.MarkNotEligibleDispatcherUseCase;
import it.gabriele.truckflow.application.port.out.operational.DispatcherRepository;
import it.gabriele.truckflow.application.result.operational.DispatcherResult;

/** Application service that changes the status of operational dispatcher roles. */
public final class MarkNotEligibleDispatcherService implements MarkNotEligibleDispatcherUseCase {

  private final DispatcherRepository dispatcherRepository;

  public MarkNotEligibleDispatcherService(DispatcherRepository dispatcherRepository) {
    UseCaseValidationException.requireNonNull(dispatcherRepository, "dispatcherRepository");
    this.dispatcherRepository = dispatcherRepository;
  }

  @Override
  public DispatcherResult execute(MarkNotEligibleDispatcherCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var existingDispatcher =
        dispatcherRepository
            .findById(command.id())
            .orElseThrow(() -> new ResourceNotFoundException("Dispatcher", command.id()));
    var updatedDispatcher = DispatcherMutationSupport.copyOf(existingDispatcher);
    updatedDispatcher.markNotEligible(command.updatedBy());

    return DispatcherResult.from(dispatcherRepository.save(updatedDispatcher));
  }
}
