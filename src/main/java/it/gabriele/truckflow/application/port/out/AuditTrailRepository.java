package it.gabriele.truckflow.application.port.out;

import it.gabriele.truckflow.domain.audit.AuditTrail;

/** Repository port per AuditTrail. L'implementazione sarà in infrastructure. */
public interface AuditTrailRepository extends RepositoryPort<AuditTrail> {}
