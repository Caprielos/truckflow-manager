package it.gabriele.truckflow.application.usecase.alerting;

import it.gabriele.truckflow.application.port.in.alerting.AcknowledgeAlertUseCase;
import it.gabriele.truckflow.application.port.out.alerting.AlertEventRepository;
import it.gabriele.truckflow.domain.alerting.AlertEvent;
import java.util.Objects;

/** Caso d'uso: prendere in carico un alert operativo. */
public final class DefaultAcknowledgeAlertUseCase implements AcknowledgeAlertUseCase {

  private final AlertEventRepository alertRepository;

  public DefaultAcknowledgeAlertUseCase(AlertEventRepository alertRepository) {
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public AlertEvent handle(Command command) {
    Objects.requireNonNull(command, "Il comando presa in carico alert è obbligatorio.");
    AlertEvent alert = alertRepository.getRequired(command.alertCode(), "Alert");
    AlertEvent acknowledged = alert.acknowledge(command.acknowledgedAt());
    alertRepository.save(acknowledged);
    return acknowledged;
  }
}
