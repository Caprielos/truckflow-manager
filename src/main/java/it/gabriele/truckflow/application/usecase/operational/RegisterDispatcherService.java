package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.application.command.operational.RegisterDispatcherCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.operational.RegisterDispatcherUseCase;
import it.gabriele.truckflow.application.port.out.operational.DispatcherRepository;
import it.gabriele.truckflow.application.result.operational.DispatcherResult;
import it.gabriele.truckflow.domain.operational.dispatcher.Dispatcher;

/** Application service that registers operational dispatcher roles. */
public final class RegisterDispatcherService implements RegisterDispatcherUseCase {

  private final DispatcherRepository dispatcherRepository;

  public RegisterDispatcherService(DispatcherRepository dispatcherRepository) {
    UseCaseValidationException.requireNonNull(dispatcherRepository, "dispatcherRepository");
    this.dispatcherRepository = dispatcherRepository;
  }

  @Override
  public DispatcherResult execute(RegisterDispatcherCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    if (dispatcherRepository.existsByCode(command.code())) {
      throw new DuplicateResourceException("Dispatcher", command.code().value());
    }

    if (dispatcherRepository.existsByUserId(command.userId())) {
      throw new DuplicateResourceException("Dispatcher", command.userId().value());
    }

    var dispatcher =
        new Dispatcher(
            null,
            command.code(),
            command.userId(),
            command.profile(),
            command.scopes(),
            command.status(),
            command.metadata(),
            command.notes());

    return DispatcherResult.from(dispatcherRepository.save(dispatcher));
  }
}
