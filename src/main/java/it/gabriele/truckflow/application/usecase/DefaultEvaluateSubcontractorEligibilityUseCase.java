package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.EvaluateSubcontractorEligibilityUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.SupplierContractRepository;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.suppliercontract.*;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/** Implementazione default di EvaluateSubcontractorEligibilityUseCase. */
public final class DefaultEvaluateSubcontractorEligibilityUseCase
    implements EvaluateSubcontractorEligibilityUseCase {

  private final SupplierContractRepository contractRepository;
  private final AlertEventRepository alertRepository;

  public DefaultEvaluateSubcontractorEligibilityUseCase(
      SupplierContractRepository contractRepository, AlertEventRepository alertRepository) {
    this.contractRepository = contractRepository;
    this.alertRepository = alertRepository;
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    SupplierContract contract =
        contractRepository.getRequired(command.contractCode(), "Contratto fornitore");
    if (SupplierContractRules.canAssignMission(
        contract, command.requiredService(), command.missionDate()))
      return EnterpriseValidationResult.passed("Sub-vettore assegnabile.");
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "SUB",
            command.contractCode(),
            "BLOCK",
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.CRITICAL,
            AlertSourceType.SYSTEM,
            "Sub-vettore non assegnabile",
            "Contratto, assicurazione, documenti o SLA non validi.",
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(
        true, List.of("Sub-vettore non eleggibile."), Optional.of(alert));
  }
}
