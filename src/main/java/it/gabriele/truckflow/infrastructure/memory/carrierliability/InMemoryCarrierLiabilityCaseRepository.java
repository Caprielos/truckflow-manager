package it.gabriele.truckflow.infrastructure.memory.carrierliability;

import it.gabriele.truckflow.application.port.out.carrierliability.CarrierLiabilityCaseRepository;
import it.gabriele.truckflow.domain.carrierliability.CarrierLiabilityCase;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per CarrierLiabilityCase. */
public final class InMemoryCarrierLiabilityCaseRepository
    extends InMemoryRepository<CarrierLiabilityCase> implements CarrierLiabilityCaseRepository {

  public InMemoryCarrierLiabilityCaseRepository() {
    super(liabilityCase -> liabilityCase.caseCode());
  }
}
