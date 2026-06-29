package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.KpiThresholdRepository;
import it.gabriele.truckflow.domain.kpi.KpiThreshold;

/** Repository in memoria per soglie KPI. */
public final class InMemoryKpiThresholdRepository extends InMemoryRepository<KpiThreshold>
    implements KpiThresholdRepository {

  public InMemoryKpiThresholdRepository() {
    super(threshold -> threshold.getMetric().name());
  }
}
