package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;

public interface EvaluateSecurityPolicyUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(String policyCode, String action, boolean mfaPassed) {}
}
