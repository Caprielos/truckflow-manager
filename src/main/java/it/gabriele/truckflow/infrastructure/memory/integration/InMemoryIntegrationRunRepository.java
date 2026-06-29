package it.gabriele.truckflow.infrastructure.memory.integration;

import it.gabriele.truckflow.application.port.out.IntegrationRunRepository;
import it.gabriele.truckflow.domain.integration.IntegrationRun;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per run integrazione. */
public final class InMemoryIntegrationRunRepository extends InMemoryRepository<IntegrationRun>
    implements IntegrationRunRepository {

  public InMemoryIntegrationRunRepository() {
    super(IntegrationRun::getRunCode);
  }
}
