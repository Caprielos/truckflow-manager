package it.gabriele.truckflow.application.usecase.integration;

import it.gabriele.truckflow.application.port.in.integration.RegisterIntegrationConnectorUseCase;
import it.gabriele.truckflow.application.port.out.IntegrationConnectorRepository;
import it.gabriele.truckflow.domain.integration.IntegrationConnector;
import java.util.Objects;

/** Caso d'uso: registrare connettore integrazione enterprise. */
public final class DefaultRegisterIntegrationConnectorUseCase
    implements RegisterIntegrationConnectorUseCase {

  private final IntegrationConnectorRepository connectorRepository;

  public DefaultRegisterIntegrationConnectorUseCase(
      IntegrationConnectorRepository connectorRepository) {
    this.connectorRepository =
        Objects.requireNonNull(connectorRepository, "Il repository connettori è obbligatorio.");
  }

  @Override
  public IntegrationConnector handle(Command command) {
    Objects.requireNonNull(command, "Il comando connettore integrazione è obbligatorio.");
    IntegrationConnector connector =
        Objects.requireNonNull(command.connector(), "Il connettore integrazione è obbligatorio.");
    connectorRepository.save(connector);
    return connector;
  }
}
