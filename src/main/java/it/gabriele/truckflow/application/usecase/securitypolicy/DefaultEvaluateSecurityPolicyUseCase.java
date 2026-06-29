package it.gabriele.truckflow.application.usecase.securitypolicy;

import it.gabriele.truckflow.application.port.in.securitypolicy.EvaluateSecurityPolicyUseCase;
import it.gabriele.truckflow.application.port.out.alerting.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.securitypolicy.EnterpriseAccessPolicyRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.securitypolicy.*;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/** Implementazione default di EvaluateSecurityPolicyUseCase. */
public final class DefaultEvaluateSecurityPolicyUseCase implements EvaluateSecurityPolicyUseCase {

  private final EnterpriseAccessPolicyRepository policyRepository;
  private final AlertEventRepository alertRepository;

  public DefaultEvaluateSecurityPolicyUseCase(
      EnterpriseAccessPolicyRepository policyRepository, AlertEventRepository alertRepository) {
    this.policyRepository = policyRepository;
    this.alertRepository = alertRepository;
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    EnterpriseAccessPolicy policy =
        policyRepository.getRequired(command.policyCode(), "Policy sicurezza");
    if (SecurityPolicyRules.canPerform(policy, command.action(), command.mfaPassed())) {
      return EnterpriseValidationResult.passed("Azione autorizzata.");
    }
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "SEC",
            command.policyCode(),
            "DENY",
            AlertType.OTHER,
            AlertSeverity.HIGH,
            AlertSourceType.SYSTEM,
            "Accesso negato",
            "Permesso mancante o MFA non superata.",
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(
        true, List.of("Azione non autorizzata."), Optional.of(alert));
  }
}
