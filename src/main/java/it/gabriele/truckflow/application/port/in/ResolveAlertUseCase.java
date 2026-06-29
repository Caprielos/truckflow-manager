package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;

public interface ResolveAlertUseCase {

  AlertEvent handle(Command command);

  record Command(String alertCode, Instant resolvedAt, Notes resolutionNotes) {}
}
