package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.AuditTrailRepository;
import it.gabriele.truckflow.domain.audit.AuditTrail;

/** Repository in memoria per AuditTrail. */
public final class InMemoryAuditTrailRepository extends InMemoryRepository<AuditTrail>
    implements AuditTrailRepository {

  public InMemoryAuditTrailRepository() {
    super(item -> item.getAggregateType() + ":" + item.getAggregateId());
  }
}
