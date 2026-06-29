package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.integration.IntegrationConnector;

public interface RegisterIntegrationConnectorUseCase {

  IntegrationConnector handle(Command command);

  record Command(IntegrationConnector connector) {}
}
