package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.RegisterServiceLevelAgreementUseCase;
import it.gabriele.truckflow.application.port.out.ServiceLevelAgreementRepository;
import it.gabriele.truckflow.domain.sla.ServiceLevelAgreement;
import java.util.Objects;

/** Caso d'uso: registrare un accordo SLA contrattuale. */
public final class DefaultRegisterServiceLevelAgreementUseCase
    implements RegisterServiceLevelAgreementUseCase {

  private final ServiceLevelAgreementRepository agreementRepository;

  public DefaultRegisterServiceLevelAgreementUseCase(
      ServiceLevelAgreementRepository agreementRepository) {
    this.agreementRepository =
        Objects.requireNonNull(agreementRepository, "Il repository SLA è obbligatorio.");
  }

  @Override
  public ServiceLevelAgreement handle(Command command) {
    Objects.requireNonNull(command, "Il comando registrazione SLA è obbligatorio.");
    ServiceLevelAgreement agreement =
        Objects.requireNonNull(command.agreement(), "Lo SLA da registrare è obbligatorio.");
    agreementRepository.save(agreement);
    return agreement;
  }
}
