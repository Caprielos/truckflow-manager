package it.gabriele.truckflow.application.usecase.oversized;

import it.gabriele.truckflow.application.port.in.ValidateOversizedComplianceUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.OversizedLoadProfileRepository;
import it.gabriele.truckflow.application.port.out.OversizedPermitRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.oversized.*;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/** Implementazione default di ValidateOversizedComplianceUseCase. */
public final class DefaultValidateOversizedComplianceUseCase
    implements ValidateOversizedComplianceUseCase {

  private final OversizedPermitRepository permitRepository;
  private final OversizedLoadProfileRepository loadRepository;
  private final AlertEventRepository alertRepository;

  public DefaultValidateOversizedComplianceUseCase(
      OversizedPermitRepository permitRepository,
      OversizedLoadProfileRepository loadRepository,
      AlertEventRepository alertRepository) {
    this.permitRepository = permitRepository;
    this.loadRepository = loadRepository;
    this.alertRepository = alertRepository;
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    OversizedPermit permit =
        permitRepository.getRequired(command.permitCode(), "Permesso eccezionale");
    OversizedLoadProfile load =
        loadRepository.getRequired(command.loadCode(), "Carico eccezionale");
    if (OversizedTransportRules.canDepart(permit, load, command.date(), command.countryCode())) {
      return EnterpriseValidationResult.passed("Compliance trasporto eccezionale verificata.");
    }
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "OVER",
            command.permitCode(),
            "BLOCK",
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.CRITICAL,
            AlertSourceType.COMPLIANCE,
            "Trasporto eccezionale non conforme",
            "Permesso, dimensioni, scorta, paese o comunicazione non validi.",
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(
        true, List.of("Trasporto eccezionale bloccato."), Optional.of(alert));
  }
}
