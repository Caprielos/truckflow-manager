package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.CarrierLiabilityCaseRepository;
import it.gabriele.truckflow.domain.carrierliability.CarrierLiabilityCase;

/** Repository in memoria per CarrierLiabilityCase. */
public final class InMemoryCarrierLiabilityCaseRepository
    extends InMemoryRepository<CarrierLiabilityCase> implements CarrierLiabilityCaseRepository {

  public InMemoryCarrierLiabilityCaseRepository() {
    super(liabilityCase -> liabilityCase.caseCode());
  }
}
