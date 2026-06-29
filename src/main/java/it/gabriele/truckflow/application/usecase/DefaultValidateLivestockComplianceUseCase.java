package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.ValidateLivestockComplianceUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.LivestockTripPlanRepository;
import it.gabriele.truckflow.application.port.out.LivestockVehicleProfileRepository;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.livestock.*;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/** Implementazione default di ValidateLivestockComplianceUseCase. */
public final class DefaultValidateLivestockComplianceUseCase
    implements ValidateLivestockComplianceUseCase {

  private final LivestockVehicleProfileRepository vehicleRepository;
  private final LivestockTripPlanRepository tripRepository;
  private final AlertEventRepository alertRepository;

  public DefaultValidateLivestockComplianceUseCase(
      LivestockVehicleProfileRepository vehicleRepository,
      LivestockTripPlanRepository tripRepository,
      AlertEventRepository alertRepository) {
    this.vehicleRepository = vehicleRepository;
    this.tripRepository = tripRepository;
    this.alertRepository = alertRepository;
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    LivestockVehicleProfile profile =
        vehicleRepository.getRequired(command.vehicleCode(), "Profilo mezzo animali");
    LivestockTripPlan trip =
        tripRepository.getRequired(command.tripCode(), "Piano viaggio animali");
    if (LivestockRules.canDepart(profile, trip, command.date())) {
      return EnterpriseValidationResult.passed("Compliance animali vivi verificata.");
    }
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "LIVE",
            command.tripCode(),
            "BLOCK",
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.CRITICAL,
            AlertSourceType.COMPLIANCE,
            "Trasporto animali non conforme",
            "Spazio, ventilazione, documenti o soste non validi.",
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(
        true, List.of("Trasporto animali non autorizzabile."), Optional.of(alert));
  }
}
