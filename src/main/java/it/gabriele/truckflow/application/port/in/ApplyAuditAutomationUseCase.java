package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.enterpriseevent.AuditDecision;

public interface ApplyAuditAutomationUseCase {
  AuditDecision handle(Command command);

  record Command(String eventId) {}
}
