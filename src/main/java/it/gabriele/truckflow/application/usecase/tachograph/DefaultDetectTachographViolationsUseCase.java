package it.gabriele.truckflow.application.usecase.tachograph;

import it.gabriele.truckflow.application.port.in.DetectTachographViolationsUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.DrivingTimeViolationRepository;
import it.gabriele.truckflow.application.port.out.TachographActivityRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.tachograph.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/** Implementazione default di DetectTachographViolationsUseCase. */
public final class DefaultDetectTachographViolationsUseCase
    implements DetectTachographViolationsUseCase {

  private final TachographActivityRepository activityRepository;
  private final DrivingTimeViolationRepository violationRepository;
  private final AlertEventRepository alertRepository;

  public DefaultDetectTachographViolationsUseCase(
      TachographActivityRepository activityRepository,
      DrivingTimeViolationRepository violationRepository,
      AlertEventRepository alertRepository) {
    this.activityRepository =
        Objects.requireNonNull(activityRepository, "Il repository attività è obbligatorio.");
    this.violationRepository =
        Objects.requireNonNull(violationRepository, "Il repository violazioni è obbligatorio.");
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public Result handle(Command command) {
    Objects.requireNonNull(command, "Il comando tachigrafo è obbligatorio.");
    List<TachographActivity> activities =
        activityRepository.findAll().stream()
            .filter(a -> a.driverCode().equals(command.driverCode().trim().toUpperCase()))
            .toList();
    long minutes = TachographRules.totalDrivingMinutes(activities);
    boolean exceeded = TachographRules.exceedsDailyDrivingLimit(activities);
    List<DrivingTimeViolation> violations =
        violationRepository.findAll().stream()
            .filter(v -> v.driverCode().equals(command.driverCode().trim().toUpperCase()))
            .filter(TachographRules::violationRequiresAlert)
            .toList();
    if (exceeded || !violations.isEmpty()) {
      AlertEvent alert =
          EnterpriseAlertFactory.open(
              "TACHO",
              command.driverCode(),
              "RISK",
              AlertType.DRIVER_HOURS_RISK,
              AlertSeverity.CRITICAL,
              AlertSourceType.DRIVER,
              "Rischio ore guida",
              "Violazioni tachigrafo o limite giornaliero superato.",
              Instant.now(),
              Notes.empty());
      alertRepository.save(alert);
    }
    return new Result(minutes, exceeded, violations);
  }
}
