package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import java.time.LocalDateTime;

public interface EvaluateDriverAssignmentUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(String driverCode, LocalDateTime missionStart) {}
}
