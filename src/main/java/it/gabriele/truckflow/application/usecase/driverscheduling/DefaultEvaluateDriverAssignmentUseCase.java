package it.gabriele.truckflow.application.usecase.driverscheduling;

import it.gabriele.truckflow.application.port.in.driverscheduling.EvaluateDriverAssignmentUseCase;
import it.gabriele.truckflow.application.port.out.alerting.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.driverscheduling.DriverAbsenceRepository;
import it.gabriele.truckflow.application.port.out.driverscheduling.DriverDisciplinaryRecordRepository;
import it.gabriele.truckflow.application.port.out.driverscheduling.DriverShiftRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.driverscheduling.*;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/** Implementazione default di EvaluateDriverAssignmentUseCase. */
public final class DefaultEvaluateDriverAssignmentUseCase
    implements EvaluateDriverAssignmentUseCase {

  private final DriverShiftRepository shiftRepository;
  private final DriverAbsenceRepository absenceRepository;
  private final DriverDisciplinaryRecordRepository disciplinaryRepository;
  private final AlertEventRepository alertRepository;

  public DefaultEvaluateDriverAssignmentUseCase(
      DriverShiftRepository shiftRepository,
      DriverAbsenceRepository absenceRepository,
      DriverDisciplinaryRecordRepository disciplinaryRepository,
      AlertEventRepository alertRepository) {
    this.shiftRepository = shiftRepository;
    this.absenceRepository = absenceRepository;
    this.disciplinaryRepository = disciplinaryRepository;
    this.alertRepository = alertRepository;
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    List<DriverAbsence> absences = absenceRepository.findAll();
    List<DriverDisciplinaryRecord> records = disciplinaryRepository.findAll();
    boolean available =
        DriverSchedulingRules.isAvailableForMission(
            command.driverCode(), command.missionStart(), absences, records);
    if (available) return EnterpriseValidationResult.passed("Autista assegnabile alla missione.");
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "DRV",
            command.driverCode(),
            "BLOCK",
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.CRITICAL,
            AlertSourceType.DRIVER,
            "Autista non assegnabile",
            "Assenza o blocco disciplinare presente.",
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(
        true, List.of("Autista non disponibile o bloccato."), Optional.of(alert));
  }
}
