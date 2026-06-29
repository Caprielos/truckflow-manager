package it.gabriele.truckflow.application.port.in.kpi;

import it.gabriele.truckflow.domain.kpi.KpiResult;

public interface RegisterKpiResultUseCase {

  KpiResult handle(Command command);

  record Command(KpiResult result) {}
}
