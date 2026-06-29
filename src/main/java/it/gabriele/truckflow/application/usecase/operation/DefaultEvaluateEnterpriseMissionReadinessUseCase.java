package it.gabriele.truckflow.application.usecase.operation;

import it.gabriele.truckflow.application.port.in.operation.EvaluateEnterpriseMissionReadinessUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.TransportRegulatorySelectionRepository;
import it.gabriele.truckflow.application.port.out.VehicleRoadUnitProfileRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.regulation.RegulatoryConfigurationRules;
import it.gabriele.truckflow.domain.regulation.TransportRegulatorySelection;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Implementazione default di EvaluateEnterpriseMissionReadinessUseCase. */
public final class DefaultEvaluateEnterpriseMissionReadinessUseCase
    implements EvaluateEnterpriseMissionReadinessUseCase {

  private final TransportRegulatorySelectionRepository selectionRepository;
  private final VehicleRoadUnitProfileRepository vehicleRepository;
  private final AlertEventRepository alertRepository;

  public DefaultEvaluateEnterpriseMissionReadinessUseCase(
      TransportRegulatorySelectionRepository selectionRepository,
      VehicleRoadUnitProfileRepository vehicleRepository,
      AlertEventRepository alertRepository) {
    this.selectionRepository = selectionRepository;
    this.vehicleRepository = vehicleRepository;
    this.alertRepository = alertRepository;
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    List<String> messages = new ArrayList<>();
    TransportRegulatorySelection selection =
        selectionRepository.getRequired(command.tenantCode(), "Selezione normativa");
    if (!RegulatoryConfigurationRules.canOperateWithFullCompliance(selection))
      messages.add("Paese normativo non configurato completamente.");
    if (!vehicleRepository.existsById(command.vehicleUnitCode()))
      messages.add("Profilo fisico mezzo non registrato.");
    if (command.requiredRegulatoryChecks() != null) {
      command.requiredRegulatoryChecks().stream()
          .filter(code -> !selection.requires(code))
          .forEach(code -> messages.add("Requisito normativo non attivo: " + code));
    }
    long criticalAlerts =
        alertRepository.findAll().stream()
            .filter(
                alert ->
                    alert.getSourceCode().equalsIgnoreCase(command.missionCode())
                        || alert.getSourceCode().equalsIgnoreCase(command.vehicleUnitCode())
                        || alert.getSourceCode().equalsIgnoreCase(command.driverCode()))
            .filter(alert -> alert.getSeverity().requiresEscalation())
            .count();
    if (criticalAlerts > 0)
      messages.add("Sono presenti alert critici collegati a missione, mezzo o autista.");
    if (messages.isEmpty())
      return EnterpriseValidationResult.passed("Missione enterprise pronta per dispatch.");
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "READY",
            command.missionCode(),
            "BLOCK",
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.CRITICAL,
            AlertSourceType.MISSION,
            "Missione non pronta",
            String.join(" ", messages),
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(true, messages, Optional.of(alert));
  }
}
