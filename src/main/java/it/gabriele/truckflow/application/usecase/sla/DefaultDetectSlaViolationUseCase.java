package it.gabriele.truckflow.application.usecase.sla;

import it.gabriele.truckflow.application.port.in.sla.DetectSlaViolationUseCase;
import it.gabriele.truckflow.application.port.out.alerting.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.sla.ServiceLevelAgreementRepository;
import it.gabriele.truckflow.application.port.out.sla.SlaViolationRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.alerting.AlertSeverity;
import it.gabriele.truckflow.domain.alerting.AlertSourceType;
import it.gabriele.truckflow.domain.alerting.AlertType;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.sla.PenaltyRule;
import it.gabriele.truckflow.domain.sla.ServiceLevelAgreement;
import it.gabriele.truckflow.domain.sla.SlaRule;
import it.gabriele.truckflow.domain.sla.SlaRules;
import it.gabriele.truckflow.domain.sla.SlaViolation;
import java.util.Objects;
import java.util.Optional;

/** Caso d'uso: rilevare una violazione SLA e generare penale/alert. */
public final class DefaultDetectSlaViolationUseCase implements DetectSlaViolationUseCase {

  private final ServiceLevelAgreementRepository agreementRepository;
  private final SlaViolationRepository violationRepository;
  private final AlertEventRepository alertRepository;

  public DefaultDetectSlaViolationUseCase(
      ServiceLevelAgreementRepository agreementRepository,
      SlaViolationRepository violationRepository,
      AlertEventRepository alertRepository) {
    this.agreementRepository =
        Objects.requireNonNull(agreementRepository, "Il repository SLA è obbligatorio.");
    this.violationRepository =
        Objects.requireNonNull(violationRepository, "Il repository violazioni SLA è obbligatorio.");
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public Result handle(Command command) {
    Objects.requireNonNull(command, "Il comando rilevazione SLA è obbligatorio.");
    ServiceLevelAgreement agreement =
        agreementRepository.getRequired(command.agreementCode(), "Accordo SLA");
    SlaRule rule =
        agreement.getRules().stream()
            .filter(candidate -> candidate.getMetric() == command.metric())
            .findFirst()
            .orElseThrow(
                () -> new IllegalArgumentException("Lo SLA non contiene la metrica richiesta."));

    boolean violated =
        SlaRules.isLate(command.plannedAt(), command.actualAt(), rule.getAllowedMinutes());
    if (!violated) {
      return new Result(false, Optional.empty(), Optional.empty());
    }

    Money penaltyAmount =
        agreement.getPenalties().stream()
            .filter(penalty -> penalty.getMetric() == command.metric())
            .map(PenaltyRule::getFixedAmount)
            .findFirst()
            .orElse(null);
    SlaViolation violation =
        SlaViolation.detected(
            command.violationCode(),
            agreement.getAgreementCode(),
            command.metric(),
            command.referenceCode(),
            command.actualAt(),
            penaltyAmount,
            Notes.of("Violazione SLA rilevata automaticamente."));
    violationRepository.save(violation);

    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "SLA",
            violation.getViolationCode(),
            "VIOLATION",
            AlertType.SLA_VIOLATION,
            rule.isCritical() ? AlertSeverity.CRITICAL : AlertSeverity.HIGH,
            AlertSourceType.SLA,
            "Violazione SLA",
            "Violazione " + command.metric() + " sul riferimento " + command.referenceCode(),
            command.evaluatedAt(),
            Notes.of("Alert generato automaticamente da controllo SLA."));
    if (!alertRepository.existsById(alert.getAlertCode())) {
      alertRepository.save(alert);
    }

    return new Result(true, Optional.of(violation), Optional.of(alert));
  }
}
