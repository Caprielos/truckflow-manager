package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.FindDispatcherCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.FindDispatcherUseCase;
import it.gabriele.truckflow.application.port.out.operational.DispatcherRepository;
import it.gabriele.truckflow.application.result.operational.DispatcherResult;

/** Application service that finds operational dispatcher roles. */
public final class FindDispatcherService implements FindDispatcherUseCase {

  private final DispatcherRepository dispatcherRepository;

  public FindDispatcherService(DispatcherRepository dispatcherRepository) {
    UseCaseValidationException.requireNonNull(dispatcherRepository, "dispatcherRepository");
    this.dispatcherRepository = dispatcherRepository;
  }

  @Override
  public DispatcherResult execute(FindDispatcherCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    return dispatcherRepository
        .findById(command.id())
        .map(DispatcherResult::from)
        .orElseThrow(() -> new ResourceNotFoundException("Dispatcher", command.id()));
  }
}
