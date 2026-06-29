package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.sla.SlaMetric;
import it.gabriele.truckflow.domain.sla.SlaViolation;
import java.time.Instant;
import java.util.Optional;

public interface DetectSlaViolationUseCase {

  Result handle(Command command);

  record Command(
      String violationCode,
      String agreementCode,
      SlaMetric metric,
      String referenceCode,
      Instant plannedAt,
      Instant actualAt,
      Instant evaluatedAt) {}

  record Result(boolean violated, Optional<SlaViolation> violation, Optional<AlertEvent> alert) {}
}
