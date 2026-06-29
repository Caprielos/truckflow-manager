package it.gabriele.truckflow.application.usecase.tachograph;

import it.gabriele.truckflow.application.port.in.tachograph.RegisterDrivingTimeViolationUseCase;
import it.gabriele.truckflow.application.port.out.tachograph.DrivingTimeViolationRepository;
import it.gabriele.truckflow.domain.tachograph.DrivingTimeViolation;
import java.util.Objects;

/** Implementazione default di RegisterDrivingTimeViolationUseCase. */
public final class DefaultRegisterDrivingTimeViolationUseCase
    implements RegisterDrivingTimeViolationUseCase {

  private final DrivingTimeViolationRepository repository;

  public DefaultRegisterDrivingTimeViolationUseCase(DrivingTimeViolationRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public DrivingTimeViolation handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    DrivingTimeViolation aggregate =
        Objects.requireNonNull(command.violation(), "La violazione tachigrafo è obbligatoria.");
    repository.save(aggregate);
    return aggregate;
  }
}
