package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.EvaluateRegulatoryRequirementUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.TransportRegulatorySelectionRepository;
import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.alerting.AlertSeverity;
import it.gabriele.truckflow.domain.alerting.AlertSourceType;
import it.gabriele.truckflow.domain.alerting.AlertType;
import it.gabriele.truckflow.domain.regulation.RegulatoryConfigurationRules;
import it.gabriele.truckflow.domain.regulation.TransportRegulatorySelection;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Implementazione default di EvaluateRegulatoryRequirementUseCase. */
public final class DefaultEvaluateRegulatoryRequirementUseCase
    implements EvaluateRegulatoryRequirementUseCase {

  private final TransportRegulatorySelectionRepository selectionRepository;
  private final AlertEventRepository alertRepository;

  public DefaultEvaluateRegulatoryRequirementUseCase(
      TransportRegulatorySelectionRepository selectionRepository,
      AlertEventRepository alertRepository) {
    this.selectionRepository =
        Objects.requireNonNull(
            selectionRepository, "Il repository selezione normativa è obbligatorio.");
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    Objects.requireNonNull(command, "Il comando requisito normativo è obbligatorio.");
    TransportRegulatorySelection selection =
        selectionRepository.getRequired(command.tenantCode(), "Selezione normativa");
    boolean configured = RegulatoryConfigurationRules.canOperateWithFullCompliance(selection);
    boolean required =
        RegulatoryConfigurationRules.activeCountryRequires(selection, command.requirementCode());
    if (configured && required) {
      return EnterpriseValidationResult.passed("Requisito normativo attivo e paese configurato.");
    }
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "REG",
            command.tenantCode(),
            command.requirementCode().name(),
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.CRITICAL,
            AlertSourceType.COMPLIANCE,
            "Profilo normativo non completo",
            "Il paese selezionato non è configurato o il requisito non è attivo.",
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(
        true, List.of("Profilo normativo non pronto."), Optional.of(alert));
  }
}
