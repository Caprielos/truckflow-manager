package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.RegisterDriverTrainingRecordUseCase;
import it.gabriele.truckflow.application.port.out.DriverTrainingRecordRepository;
import it.gabriele.truckflow.domain.hr.DriverTrainingRecord;
import java.util.Objects;

/** Caso d'uso: registrare formazione autista. */
public final class DefaultRegisterDriverTrainingRecordUseCase
    implements RegisterDriverTrainingRecordUseCase {

  private final DriverTrainingRecordRepository trainingRepository;

  public DefaultRegisterDriverTrainingRecordUseCase(
      DriverTrainingRecordRepository trainingRepository) {
    this.trainingRepository =
        Objects.requireNonNull(trainingRepository, "Il repository formazione è obbligatorio.");
  }

  @Override
  public DriverTrainingRecord handle(Command command) {
    Objects.requireNonNull(command, "Il comando formazione è obbligatorio.");
    DriverTrainingRecord record =
        Objects.requireNonNull(command.trainingRecord(), "La formazione è obbligatoria.");
    trainingRepository.save(record);
    return record;
  }
}
