package it.gabriele.truckflow.application.usecase.kpi;

import it.gabriele.truckflow.application.port.in.EvaluateKpiThresholdUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.KpiResultRepository;
import it.gabriele.truckflow.application.port.out.KpiThresholdRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.alerting.AlertSeverity;
import it.gabriele.truckflow.domain.alerting.AlertSourceType;
import it.gabriele.truckflow.domain.alerting.AlertType;
import it.gabriele.truckflow.domain.kpi.KpiResult;
import it.gabriele.truckflow.domain.kpi.KpiRules;
import it.gabriele.truckflow.domain.kpi.KpiThreshold;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;
import java.util.Optional;

/** Caso d'uso: valutare un KPI contro soglia e aprire alert se warning/critico. */
public final class DefaultEvaluateKpiThresholdUseCase implements EvaluateKpiThresholdUseCase {

  private final KpiResultRepository resultRepository;
  private final KpiThresholdRepository thresholdRepository;
  private final AlertEventRepository alertRepository;

  public DefaultEvaluateKpiThresholdUseCase(
      KpiResultRepository resultRepository,
      KpiThresholdRepository thresholdRepository,
      AlertEventRepository alertRepository) {
    this.resultRepository =
        Objects.requireNonNull(resultRepository, "Il repository KPI è obbligatorio.");
    this.thresholdRepository =
        Objects.requireNonNull(thresholdRepository, "Il repository soglie KPI è obbligatorio.");
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public Result handle(Command command) {
    Objects.requireNonNull(command, "Il comando valutazione KPI è obbligatorio.");
    KpiResult result = Objects.requireNonNull(command.result(), "Il risultato KPI è obbligatorio.");
    KpiThreshold threshold =
        thresholdRepository.getRequired(command.thresholdMetric().name(), "Soglia KPI");

    boolean critical = KpiRules.isCritical(result, threshold);
    boolean warning = critical || KpiRules.isWarning(result, threshold);
    resultRepository.save(result);

    if (!warning) {
      return new Result(result, false, false, Optional.empty());
    }

    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "KPI",
            result.getResultCode(),
            critical ? "CRITICAL" : "WARNING",
            AlertType.OTHER,
            critical ? AlertSeverity.CRITICAL : AlertSeverity.WARNING,
            AlertSourceType.SYSTEM,
            critical ? "KPI critico" : "KPI in warning",
            "KPI " + result.getMetric() + " = " + result.getValue() + " " + result.getUnit(),
            command.evaluatedAt(),
            Notes.of("Alert generato automaticamente da soglia KPI."));
    if (!alertRepository.existsById(alert.getAlertCode())) {
      alertRepository.save(alert);
    }

    return new Result(result, warning, critical, Optional.of(alert));
  }
}
