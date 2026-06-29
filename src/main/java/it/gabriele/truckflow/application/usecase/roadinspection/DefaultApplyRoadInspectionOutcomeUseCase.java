package it.gabriele.truckflow.application.usecase.roadinspection;

import it.gabriele.truckflow.application.port.in.ApplyRoadInspectionOutcomeUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.RoadInspectionRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.roadinspection.*;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Implementazione default di ApplyRoadInspectionOutcomeUseCase. */
public final class DefaultApplyRoadInspectionOutcomeUseCase
    implements ApplyRoadInspectionOutcomeUseCase {

  private final RoadInspectionRepository inspectionRepository;
  private final AlertEventRepository alertRepository;

  public DefaultApplyRoadInspectionOutcomeUseCase(
      RoadInspectionRepository inspectionRepository, AlertEventRepository alertRepository) {
    this.inspectionRepository = inspectionRepository;
    this.alertRepository = alertRepository;
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    RoadInspection inspection =
        inspectionRepository.getRequired(command.inspectionCode(), "Controllo su strada");
    List<String> messages = new ArrayList<>();
    if (RoadInspectionRules.blocksVehicle(inspection))
      messages.add("Il controllo blocca mezzo o missione.");
    if (RoadInspectionRules.hasFine(inspection)) messages.add("Il controllo contiene sanzioni.");
    if (messages.isEmpty())
      return EnterpriseValidationResult.passed("Controllo su strada senza blocchi.");
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "INSP",
            command.inspectionCode(),
            "OUTCOME",
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.CRITICAL,
            AlertSourceType.VEHICLE,
            "Esito controllo su strada critico",
            String.join(" ", messages),
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(
        RoadInspectionRules.blocksVehicle(inspection), messages, Optional.of(alert));
  }
}
