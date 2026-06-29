package it.gabriele.truckflow.application.usecase.alerting;

import it.gabriele.truckflow.application.port.in.alerting.ResolveAlertUseCase;
import it.gabriele.truckflow.application.port.out.alerting.AlertEventRepository;
import it.gabriele.truckflow.domain.alerting.AlertEvent;
import java.util.Objects;

/** Caso d'uso: risolvere un alert operativo. */
public final class DefaultResolveAlertUseCase implements ResolveAlertUseCase {

  private final AlertEventRepository alertRepository;

  public DefaultResolveAlertUseCase(AlertEventRepository alertRepository) {
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public AlertEvent handle(Command command) {
    Objects.requireNonNull(command, "Il comando risoluzione alert è obbligatorio.");
    AlertEvent alert = alertRepository.getRequired(command.alertCode(), "Alert");
    AlertEvent resolved = alert.resolve(command.resolvedAt(), command.resolutionNotes());
    alertRepository.save(resolved);
    return resolved;
  }
}
