package it.gabriele.truckflow.application.port.in.adr;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.cargo.AdrClass;

public interface ValidateAdrComplianceUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(String profileCode, String checklistCode, AdrClass adrClass) {}
}
