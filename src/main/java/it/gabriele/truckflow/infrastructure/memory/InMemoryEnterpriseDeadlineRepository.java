package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.EnterpriseDeadlineRepository;
import it.gabriele.truckflow.domain.deadline.EnterpriseDeadline;

/** Repository in memoria per scadenze enterprise. */
public final class InMemoryEnterpriseDeadlineRepository
    extends InMemoryRepository<EnterpriseDeadline> implements EnterpriseDeadlineRepository {

  public InMemoryEnterpriseDeadlineRepository() {
    super(EnterpriseDeadline::getDeadlineCode);
  }
}
