package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.securitypolicy.EnterpriseAccessPolicy;

public interface RegisterEnterpriseAccessPolicyUseCase {
  EnterpriseAccessPolicy handle(Command command);

  record Command(EnterpriseAccessPolicy policy) {}
}
