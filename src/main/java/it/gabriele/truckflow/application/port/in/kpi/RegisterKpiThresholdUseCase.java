package it.gabriele.truckflow.application.port.in.kpi;

import it.gabriele.truckflow.domain.kpi.KpiThreshold;

public interface RegisterKpiThresholdUseCase {

  KpiThreshold handle(Command command);

  record Command(KpiThreshold threshold) {}
}
