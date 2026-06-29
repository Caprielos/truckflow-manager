package it.gabriele.truckflow.application.port.in.carrierliability;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;

public interface EvaluateCarrierLiabilityUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(String caseCode) {}
}
