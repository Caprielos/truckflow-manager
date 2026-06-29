package it.gabriele.truckflow.application.usecase.carrierliability;

import it.gabriele.truckflow.application.port.in.carrierliability.OpenCarrierLiabilityCaseUseCase;
import it.gabriele.truckflow.application.port.out.CarrierLiabilityCaseRepository;
import it.gabriele.truckflow.domain.carrierliability.CarrierLiabilityCase;
import java.util.Objects;

/** Implementazione default di OpenCarrierLiabilityCaseUseCase. */
public final class DefaultOpenCarrierLiabilityCaseUseCase
    implements OpenCarrierLiabilityCaseUseCase {

  private final CarrierLiabilityCaseRepository repository;

  public DefaultOpenCarrierLiabilityCaseUseCase(CarrierLiabilityCaseRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public CarrierLiabilityCase handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    CarrierLiabilityCase aggregate =
        Objects.requireNonNull(
            command.liabilityCase(), "La pratica responsabilità vettore è obbligatoria.");
    repository.save(aggregate);
    return aggregate;
  }
}
