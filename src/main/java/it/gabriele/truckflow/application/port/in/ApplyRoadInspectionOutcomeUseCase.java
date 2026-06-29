package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;

public interface ApplyRoadInspectionOutcomeUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(String inspectionCode) {}
}
