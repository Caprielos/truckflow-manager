package it.gabriele.truckflow.application.usecase.driverscheduling;

import it.gabriele.truckflow.application.port.in.driverscheduling.RegisterDriverShiftUseCase;
import it.gabriele.truckflow.application.port.out.driverscheduling.DriverShiftRepository;
import it.gabriele.truckflow.domain.driverscheduling.DriverShift;
import java.util.Objects;

/** Implementazione default di RegisterDriverShiftUseCase. */
public final class DefaultRegisterDriverShiftUseCase implements RegisterDriverShiftUseCase {

  private final DriverShiftRepository repository;

  public DefaultRegisterDriverShiftUseCase(DriverShiftRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public DriverShift handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    DriverShift aggregate =
        Objects.requireNonNull(command.shift(), "Il turno autista è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
