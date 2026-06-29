package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.IntegrationRunRepository;
import it.gabriele.truckflow.domain.integration.IntegrationRun;

/** Repository in memoria per run integrazione. */
public final class InMemoryIntegrationRunRepository extends InMemoryRepository<IntegrationRun>
    implements IntegrationRunRepository {

  public InMemoryIntegrationRunRepository() {
    super(IntegrationRun::getRunCode);
  }
}
