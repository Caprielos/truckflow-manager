package it.gabriele.truckflow.application.usecase.driverscheduling;

import it.gabriele.truckflow.application.port.in.driverscheduling.RegisterDriverDisciplinaryRecordUseCase;
import it.gabriele.truckflow.application.port.out.driverscheduling.DriverDisciplinaryRecordRepository;
import it.gabriele.truckflow.domain.driverscheduling.DriverDisciplinaryRecord;
import java.util.Objects;

/** Implementazione default di RegisterDriverDisciplinaryRecordUseCase. */
public final class DefaultRegisterDriverDisciplinaryRecordUseCase
    implements RegisterDriverDisciplinaryRecordUseCase {

  private final DriverDisciplinaryRecordRepository repository;

  public DefaultRegisterDriverDisciplinaryRecordUseCase(
      DriverDisciplinaryRecordRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public DriverDisciplinaryRecord handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    DriverDisciplinaryRecord aggregate =
        Objects.requireNonNull(command.record(), "Il record disciplinare è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
