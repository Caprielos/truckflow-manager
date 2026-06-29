package it.gabriele.truckflow.infrastructure.memory.audit;

import it.gabriele.truckflow.application.port.out.AuditTrailRepository;
import it.gabriele.truckflow.domain.audit.AuditTrail;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per AuditTrail. */
public final class InMemoryAuditTrailRepository extends InMemoryRepository<AuditTrail>
    implements AuditTrailRepository {

  public InMemoryAuditTrailRepository() {
    super(item -> item.getAggregateType() + ":" + item.getAggregateId());
  }
}
