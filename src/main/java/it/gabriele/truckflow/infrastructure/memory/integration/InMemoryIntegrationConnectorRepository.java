package it.gabriele.truckflow.infrastructure.memory.integration;

import it.gabriele.truckflow.application.port.out.IntegrationConnectorRepository;
import it.gabriele.truckflow.domain.integration.IntegrationConnector;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per connettori integrazione. */
public final class InMemoryIntegrationConnectorRepository
    extends InMemoryRepository<IntegrationConnector> implements IntegrationConnectorRepository {

  public InMemoryIntegrationConnectorRepository() {
    super(IntegrationConnector::getConnectorCode);
  }
}
