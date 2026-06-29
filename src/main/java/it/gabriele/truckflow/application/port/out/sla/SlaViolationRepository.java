package it.gabriele.truckflow.application.port.out.sla;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.sla.SlaViolation;

/** Repository port per violazioni SLA e penali. */
public interface SlaViolationRepository extends RepositoryPort<SlaViolation> {}
