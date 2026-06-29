package it.gabriele.truckflow.application.usecase.carrierliability;

import it.gabriele.truckflow.application.port.in.carrierliability.EvaluateCarrierLiabilityUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.CarrierLiabilityCaseRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.carrierliability.*;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Implementazione default di EvaluateCarrierLiabilityUseCase. */
public final class DefaultEvaluateCarrierLiabilityUseCase
    implements EvaluateCarrierLiabilityUseCase {

  private final CarrierLiabilityCaseRepository liabilityRepository;
  private final AlertEventRepository alertRepository;

  public DefaultEvaluateCarrierLiabilityUseCase(
      CarrierLiabilityCaseRepository liabilityRepository, AlertEventRepository alertRepository) {
    this.liabilityRepository = liabilityRepository;
    this.alertRepository = alertRepository;
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    CarrierLiabilityCase liabilityCase =
        liabilityRepository.getRequired(command.caseCode(), "Pratica responsabilità vettore");
    List<String> messages = new ArrayList<>();
    if (CarrierLiabilityRules.requiresInsuranceNotification(liabilityCase)
        && !liabilityCase.insuranceNotified()) messages.add("Assicurazione da notificare.");
    if (CarrierLiabilityRules.requiresPoliceReport(liabilityCase)
        && !liabilityCase.policeReportAttached()) messages.add("Verbale autorità richiesto.");
    if (!CarrierLiabilityRules.isReadyForAssessment(liabilityCase))
      messages.add("Pratica non pronta per valutazione responsabilità.");
    if (messages.isEmpty())
      return EnterpriseValidationResult.passed("Pratica pronta per assessment.");
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "CMR",
            command.caseCode(),
            "REVIEW",
            AlertType.CLAIM_ESCALATION,
            AlertSeverity.HIGH,
            AlertSourceType.CLAIM,
            "Responsabilità vettore da gestire",
            String.join(" ", messages),
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(false, messages, Optional.of(alert));
  }
}
