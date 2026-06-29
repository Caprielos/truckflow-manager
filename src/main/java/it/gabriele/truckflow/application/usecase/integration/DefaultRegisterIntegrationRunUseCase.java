package it.gabriele.truckflow.application.usecase.integration;

import it.gabriele.truckflow.application.port.in.integration.RegisterIntegrationRunUseCase;
import it.gabriele.truckflow.application.port.out.alerting.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.integration.IntegrationConnectorRepository;
import it.gabriele.truckflow.application.port.out.integration.IntegrationRunRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.alerting.AlertSeverity;
import it.gabriele.truckflow.domain.alerting.AlertSourceType;
import it.gabriele.truckflow.domain.alerting.AlertType;
import it.gabriele.truckflow.domain.integration.IntegrationRules;
import it.gabriele.truckflow.domain.integration.IntegrationRun;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;
import java.util.Optional;

/** Caso d'uso: registrare un run di integrazione e aprire alert se serve riconciliazione. */
public final class DefaultRegisterIntegrationRunUseCase implements RegisterIntegrationRunUseCase {

  private final IntegrationConnectorRepository connectorRepository;
  private final IntegrationRunRepository runRepository;
  private final AlertEventRepository alertRepository;

  public DefaultRegisterIntegrationRunUseCase(
      IntegrationConnectorRepository connectorRepository,
      IntegrationRunRepository runRepository,
      AlertEventRepository alertRepository) {
    this.connectorRepository =
        Objects.requireNonNull(connectorRepository, "Il repository connettori è obbligatorio.");
    this.runRepository =
        Objects.requireNonNull(runRepository, "Il repository run integrazione è obbligatorio.");
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public Result handle(Command command) {
    Objects.requireNonNull(command, "Il comando run integrazione è obbligatorio.");
    IntegrationRun run =
        Objects.requireNonNull(command.run(), "Il run integrazione è obbligatorio.");
    connectorRepository.getRequired(run.getConnectorCode(), "Connettore integrazione");
    runRepository.save(run);

    if (!IntegrationRules.needsReconciliation(run)) {
      return new Result(run, Optional.empty());
    }

    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "INT",
            run.getRunCode(),
            "RECONCILE",
            AlertType.TELEMATICS_ANOMALY,
            AlertSeverity.HIGH,
            AlertSourceType.SYSTEM,
            "Integrazione da riconciliare",
            "Il run integrazione " + run.getRunCode() + " contiene errori o record falliti.",
            command.evaluatedAt(),
            Notes.of("Alert generato automaticamente da run integrazione."));
    if (!alertRepository.existsById(alert.getAlertCode())) {
      alertRepository.save(alert);
    }

    return new Result(run, Optional.of(alert));
  }
}
