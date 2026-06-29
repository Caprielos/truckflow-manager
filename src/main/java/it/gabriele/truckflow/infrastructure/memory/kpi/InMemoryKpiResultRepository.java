package it.gabriele.truckflow.infrastructure.memory.kpi;

import it.gabriele.truckflow.application.port.out.kpi.KpiResultRepository;
import it.gabriele.truckflow.domain.kpi.KpiResult;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per risultati KPI. */
public final class InMemoryKpiResultRepository extends InMemoryRepository<KpiResult>
    implements KpiResultRepository {

  public InMemoryKpiResultRepository() {
    super(KpiResult::getResultCode);
  }
}
