package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.integration.IntegrationRun;
import java.time.Instant;
import java.util.Optional;

public interface RegisterIntegrationRunUseCase {

  Result handle(Command command);

  record Command(IntegrationRun run, Instant evaluatedAt) {}

  record Result(IntegrationRun run, Optional<AlertEvent> alert) {}
}
