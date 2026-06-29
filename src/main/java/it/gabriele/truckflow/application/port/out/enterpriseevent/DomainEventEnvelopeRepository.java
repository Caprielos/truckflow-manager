package it.gabriele.truckflow.application.port.out.enterpriseevent;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.enterpriseevent.DomainEventEnvelope;

/** Repository port per DomainEventEnvelope. */
public interface DomainEventEnvelopeRepository extends RepositoryPort<DomainEventEnvelope> {}
