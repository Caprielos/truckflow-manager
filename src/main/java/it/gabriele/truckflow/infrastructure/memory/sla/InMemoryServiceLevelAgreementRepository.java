package it.gabriele.truckflow.infrastructure.memory.sla;

import it.gabriele.truckflow.application.port.out.ServiceLevelAgreementRepository;
import it.gabriele.truckflow.domain.sla.ServiceLevelAgreement;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per accordi SLA. */
public final class InMemoryServiceLevelAgreementRepository
    extends InMemoryRepository<ServiceLevelAgreement> implements ServiceLevelAgreementRepository {

  public InMemoryServiceLevelAgreementRepository() {
    super(ServiceLevelAgreement::getAgreementCode);
  }
}
