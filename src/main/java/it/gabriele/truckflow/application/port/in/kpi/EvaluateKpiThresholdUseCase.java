package it.gabriele.truckflow.application.port.in.kpi;

import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.kpi.KpiMetric;
import it.gabriele.truckflow.domain.kpi.KpiResult;
import java.time.Instant;
import java.util.Optional;

public interface EvaluateKpiThresholdUseCase {

  Result handle(Command command);

  record Command(KpiResult result, KpiMetric thresholdMetric, Instant evaluatedAt) {}

  record Result(KpiResult result, boolean warning, boolean critical, Optional<AlertEvent> alert) {}
}
