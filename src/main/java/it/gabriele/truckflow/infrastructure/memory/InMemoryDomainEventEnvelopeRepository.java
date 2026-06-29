package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DomainEventEnvelopeRepository;
import it.gabriele.truckflow.domain.enterpriseevent.DomainEventEnvelope;

/** Repository in memoria per DomainEventEnvelope. */
public final class InMemoryDomainEventEnvelopeRepository
    extends InMemoryRepository<DomainEventEnvelope> implements DomainEventEnvelopeRepository {

  public InMemoryDomainEventEnvelopeRepository() {
    super(event -> event.eventId());
  }
}
