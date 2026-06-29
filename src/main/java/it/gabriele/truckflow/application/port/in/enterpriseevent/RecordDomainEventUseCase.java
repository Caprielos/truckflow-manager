package it.gabriele.truckflow.application.port.in.enterpriseevent;

import it.gabriele.truckflow.domain.enterpriseevent.DomainEventEnvelope;

public interface RecordDomainEventUseCase {
  DomainEventEnvelope handle(Command command);

  record Command(DomainEventEnvelope event) {}
}
