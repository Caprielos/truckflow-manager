package it.gabriele.truckflow.application.port.out.audit;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.audit.AuditTrail;

/** Repository port per AuditTrail. L'implementazione sarà in infrastructure. */
public interface AuditTrailRepository extends RepositoryPort<AuditTrail> {}
