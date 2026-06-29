package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.EnterpriseAccessPolicyRepository;
import it.gabriele.truckflow.domain.securitypolicy.EnterpriseAccessPolicy;

/** Repository in memoria per EnterpriseAccessPolicy. */
public final class InMemoryEnterpriseAccessPolicyRepository
    extends InMemoryRepository<EnterpriseAccessPolicy> implements EnterpriseAccessPolicyRepository {

  public InMemoryEnterpriseAccessPolicyRepository() {
    super(policy -> policy.policyCode());
  }
}
