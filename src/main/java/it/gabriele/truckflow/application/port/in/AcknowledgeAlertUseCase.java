package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.alerting.AlertEvent;
import java.time.Instant;

public interface AcknowledgeAlertUseCase {

  AlertEvent handle(Command command);

  record Command(String alertCode, Instant acknowledgedAt) {}
}
