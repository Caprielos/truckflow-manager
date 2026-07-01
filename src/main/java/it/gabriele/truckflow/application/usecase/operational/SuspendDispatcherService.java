package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.SuspendDispatcherCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.SuspendDispatcherUseCase;
import it.gabriele.truckflow.application.port.out.operational.DispatcherRepository;
import it.gabriele.truckflow.application.result.operational.DispatcherResult;

/** Application service that changes the status of operational dispatcher roles. */
public final class SuspendDispatcherService implements SuspendDispatcherUseCase {

  private final DispatcherRepository dispatcherRepository;

  public SuspendDispatcherService(DispatcherRepository dispatcherRepository) {
    UseCaseValidationException.requireNonNull(dispatcherRepository, "dispatcherRepository");
    this.dispatcherRepository = dispatcherRepository;
  }

  @Override
  public DispatcherResult execute(SuspendDispatcherCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var existingDispatcher =
        dispatcherRepository
            .findById(command.id())
            .orElseThrow(() -> new ResourceNotFoundException("Dispatcher", command.id()));
    var updatedDispatcher = DispatcherMutationSupport.copyOf(existingDispatcher);
    updatedDispatcher.suspend(command.updatedBy());

    return DispatcherResult.from(dispatcherRepository.save(updatedDispatcher));
  }
}
