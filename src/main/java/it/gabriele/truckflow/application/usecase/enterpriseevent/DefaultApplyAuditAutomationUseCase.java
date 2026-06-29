package it.gabriele.truckflow.application.usecase.enterpriseevent;

import it.gabriele.truckflow.application.port.in.ApplyAuditAutomationUseCase;
import it.gabriele.truckflow.application.port.out.DomainEventEnvelopeRepository;
import it.gabriele.truckflow.domain.enterpriseevent.AuditAutomationRules;
import it.gabriele.truckflow.domain.enterpriseevent.AuditDecision;
import it.gabriele.truckflow.domain.enterpriseevent.DomainEventEnvelope;

/** Implementazione default di ApplyAuditAutomationUseCase. */
public final class DefaultApplyAuditAutomationUseCase implements ApplyAuditAutomationUseCase {

  private final DomainEventEnvelopeRepository eventRepository;

  public DefaultApplyAuditAutomationUseCase(DomainEventEnvelopeRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  public AuditDecision handle(Command command) {
    DomainEventEnvelope event = eventRepository.getRequired(command.eventId(), "Evento domain");
    return AuditAutomationRules.decide(event);
  }
}
