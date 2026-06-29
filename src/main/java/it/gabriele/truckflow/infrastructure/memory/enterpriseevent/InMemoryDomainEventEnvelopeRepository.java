package it.gabriele.truckflow.infrastructure.memory.enterpriseevent;

import it.gabriele.truckflow.application.port.out.enterpriseevent.DomainEventEnvelopeRepository;
import it.gabriele.truckflow.domain.enterpriseevent.DomainEventEnvelope;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DomainEventEnvelope. */
public final class InMemoryDomainEventEnvelopeRepository
    extends InMemoryRepository<DomainEventEnvelope> implements DomainEventEnvelopeRepository {

  public InMemoryDomainEventEnvelopeRepository() {
    super(event -> event.eventId());
  }
}
