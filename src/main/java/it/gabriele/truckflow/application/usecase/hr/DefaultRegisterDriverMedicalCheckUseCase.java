package it.gabriele.truckflow.application.usecase.hr;

import it.gabriele.truckflow.application.port.in.hr.RegisterDriverMedicalCheckUseCase;
import it.gabriele.truckflow.application.port.out.hr.DriverMedicalCheckRepository;
import it.gabriele.truckflow.domain.hr.DriverMedicalCheck;
import java.util.Objects;

/** Caso d'uso: registrare visita medica autista. */
public final class DefaultRegisterDriverMedicalCheckUseCase
    implements RegisterDriverMedicalCheckUseCase {

  private final DriverMedicalCheckRepository medicalCheckRepository;

  public DefaultRegisterDriverMedicalCheckUseCase(
      DriverMedicalCheckRepository medicalCheckRepository) {
    this.medicalCheckRepository =
        Objects.requireNonNull(
            medicalCheckRepository, "Il repository visite mediche è obbligatorio.");
  }

  @Override
  public DriverMedicalCheck handle(Command command) {
    Objects.requireNonNull(command, "Il comando visita medica è obbligatorio.");
    DriverMedicalCheck medicalCheck =
        Objects.requireNonNull(command.medicalCheck(), "La visita medica è obbligatoria.");
    medicalCheckRepository.save(medicalCheck);
    return medicalCheck;
  }
}
