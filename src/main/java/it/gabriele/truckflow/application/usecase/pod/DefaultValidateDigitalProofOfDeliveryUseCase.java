package it.gabriele.truckflow.application.usecase.pod;

import it.gabriele.truckflow.application.port.in.ValidateDigitalProofOfDeliveryUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.DigitalProofOfDeliveryRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.pod.*;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/** Implementazione default di ValidateDigitalProofOfDeliveryUseCase. */
public final class DefaultValidateDigitalProofOfDeliveryUseCase
    implements ValidateDigitalProofOfDeliveryUseCase {

  private final DigitalProofOfDeliveryRepository podRepository;
  private final AlertEventRepository alertRepository;

  public DefaultValidateDigitalProofOfDeliveryUseCase(
      DigitalProofOfDeliveryRepository podRepository, AlertEventRepository alertRepository) {
    this.podRepository = podRepository;
    this.alertRepository = alertRepository;
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    DigitalProofOfDelivery pod = podRepository.getRequired(command.podCode(), "POD digitale");
    if (PodRules.isLegallyStrong(pod))
      return EnterpriseValidationResult.passed("POD legalmente forte.");
    if (PodRules.requiresClaimReview(pod)) {
      AlertEvent alert =
          EnterpriseAlertFactory.open(
              "POD",
              command.podCode(),
              "CLAIM",
              AlertType.CLAIM_ESCALATION,
              AlertSeverity.HIGH,
              AlertSourceType.DOCUMENT,
              "POD da revisionare",
              "POD con riserve, danni o firma mancante.",
              Instant.now(),
              Notes.empty());
      alertRepository.save(alert);
      return EnterpriseValidationResult.failed(
          false, List.of("POD richiede claim review."), Optional.of(alert));
    }
    return EnterpriseValidationResult.failed(
        false, List.of("POD non ancora validabile."), Optional.empty());
  }
}
