package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.ValidatePhysicalTransportComplianceUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.VehicleRoadUnitProfileRepository;
import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.alerting.AlertSeverity;
import it.gabriele.truckflow.domain.alerting.AlertSourceType;
import it.gabriele.truckflow.domain.alerting.AlertType;
import it.gabriele.truckflow.domain.roadtransport.RoadTransportPhysicalRules;
import it.gabriele.truckflow.domain.roadtransport.VehicleRoadUnitProfile;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Implementazione default di ValidatePhysicalTransportComplianceUseCase. */
public final class DefaultValidatePhysicalTransportComplianceUseCase
    implements ValidatePhysicalTransportComplianceUseCase {

  private final VehicleRoadUnitProfileRepository profileRepository;
  private final AlertEventRepository alertRepository;

  public DefaultValidatePhysicalTransportComplianceUseCase(
      VehicleRoadUnitProfileRepository profileRepository, AlertEventRepository alertRepository) {
    this.profileRepository =
        Objects.requireNonNull(profileRepository, "Il repository profili fisici è obbligatorio.");
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    Objects.requireNonNull(command, "Il comando compliance fisica è obbligatorio.");
    VehicleRoadUnitProfile profile =
        profileRepository.getRequired(command.unitCode(), "Profilo fisico veicolo");
    List<String> messages = new ArrayList<>();
    if (!RoadTransportPhysicalRules.canCarryPayload(profile, command.payloadWeight())) {
      messages.add("Payload o carico assiale non compatibile con il mezzo.");
    }
    if (command.requiredCapability() != null
        && !RoadTransportPhysicalRules.canPerformSpecialTransport(
            profile, command.requiredCapability())) {
      messages.add("Capacità speciale richiesta non supportata dal mezzo.");
    }
    if (messages.isEmpty()) {
      return EnterpriseValidationResult.passed("Compliance fisica mezzo verificata.");
    }
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "PHY",
            command.unitCode(),
            "BLOCK",
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.CRITICAL,
            AlertSourceType.VEHICLE,
            "Mezzo fisicamente non idoneo",
            String.join(" ", messages),
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(true, messages, Optional.of(alert));
  }
}
