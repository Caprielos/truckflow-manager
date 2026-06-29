package it.gabriele.truckflow.application.usecase.enterpriseevent;

import it.gabriele.truckflow.application.port.in.enterpriseevent.RecordDomainEventUseCase;
import it.gabriele.truckflow.application.port.out.DomainEventEnvelopeRepository;
import it.gabriele.truckflow.domain.enterpriseevent.DomainEventEnvelope;
import java.util.Objects;

/** Implementazione default di RecordDomainEventUseCase. */
public final class DefaultRecordDomainEventUseCase implements RecordDomainEventUseCase {

  private final DomainEventEnvelopeRepository repository;

  public DefaultRecordDomainEventUseCase(DomainEventEnvelopeRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public DomainEventEnvelope handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    DomainEventEnvelope aggregate =
        Objects.requireNonNull(command.event(), "L evento domain è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
