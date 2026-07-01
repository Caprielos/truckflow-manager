package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.ActivateDispatcherCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.ActivateDispatcherUseCase;
import it.gabriele.truckflow.application.port.out.operational.DispatcherRepository;
import it.gabriele.truckflow.application.result.operational.DispatcherResult;

/** Application service that changes the status of operational dispatcher roles. */
public final class ActivateDispatcherService implements ActivateDispatcherUseCase {

  private final DispatcherRepository dispatcherRepository;

  public ActivateDispatcherService(DispatcherRepository dispatcherRepository) {
    UseCaseValidationException.requireNonNull(dispatcherRepository, "dispatcherRepository");
    this.dispatcherRepository = dispatcherRepository;
  }

  @Override
  public DispatcherResult execute(ActivateDispatcherCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var existingDispatcher =
        dispatcherRepository
            .findById(command.id())
            .orElseThrow(() -> new ResourceNotFoundException("Dispatcher", command.id()));
    var updatedDispatcher = DispatcherMutationSupport.copyOf(existingDispatcher);
    updatedDispatcher.activate(command.updatedBy());

    return DispatcherResult.from(dispatcherRepository.save(updatedDispatcher));
  }
}
