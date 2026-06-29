package it.gabriele.truckflow.application.usecase.driverscheduling;

import it.gabriele.truckflow.application.port.in.driverscheduling.RegisterDriverAbsenceUseCase;
import it.gabriele.truckflow.application.port.out.DriverAbsenceRepository;
import it.gabriele.truckflow.domain.driverscheduling.DriverAbsence;
import java.util.Objects;

/** Implementazione default di RegisterDriverAbsenceUseCase. */
public final class DefaultRegisterDriverAbsenceUseCase implements RegisterDriverAbsenceUseCase {

  private final DriverAbsenceRepository repository;

  public DefaultRegisterDriverAbsenceUseCase(DriverAbsenceRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public DriverAbsence handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    DriverAbsence aggregate =
        Objects.requireNonNull(command.absence(), "L assenza autista è obbligatoria.");
    repository.save(aggregate);
    return aggregate;
  }
}
