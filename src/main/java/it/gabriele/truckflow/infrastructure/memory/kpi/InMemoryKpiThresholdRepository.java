package it.gabriele.truckflow.infrastructure.memory.kpi;

import it.gabriele.truckflow.application.port.out.KpiThresholdRepository;
import it.gabriele.truckflow.domain.kpi.KpiThreshold;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per soglie KPI. */
public final class InMemoryKpiThresholdRepository extends InMemoryRepository<KpiThreshold>
    implements KpiThresholdRepository {

  public InMemoryKpiThresholdRepository() {
    super(threshold -> threshold.getMetric().name());
  }
}
