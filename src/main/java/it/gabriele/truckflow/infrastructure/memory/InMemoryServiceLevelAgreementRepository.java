package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.ServiceLevelAgreementRepository;
import it.gabriele.truckflow.domain.sla.ServiceLevelAgreement;

/** Repository in memoria per accordi SLA. */
public final class InMemoryServiceLevelAgreementRepository
    extends InMemoryRepository<ServiceLevelAgreement> implements ServiceLevelAgreementRepository {

  public InMemoryServiceLevelAgreementRepository() {
    super(ServiceLevelAgreement::getAgreementCode);
  }
}
