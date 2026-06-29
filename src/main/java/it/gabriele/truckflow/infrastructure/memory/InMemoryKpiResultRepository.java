package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.KpiResultRepository;
import it.gabriele.truckflow.domain.kpi.KpiResult;

/** Repository in memoria per risultati KPI. */
public final class InMemoryKpiResultRepository extends InMemoryRepository<KpiResult>
    implements KpiResultRepository {

  public InMemoryKpiResultRepository() {
    super(KpiResult::getResultCode);
  }
}
