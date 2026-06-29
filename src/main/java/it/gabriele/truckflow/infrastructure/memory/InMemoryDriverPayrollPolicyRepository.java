package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DriverPayrollPolicyRepository;
import it.gabriele.truckflow.domain.payroll.DriverPayrollPolicy;

/** Repository in memoria per DriverPayrollPolicy. */
public final class InMemoryDriverPayrollPolicyRepository
    extends InMemoryRepository<DriverPayrollPolicy> implements DriverPayrollPolicyRepository {

  public InMemoryDriverPayrollPolicyRepository() {
    super(item -> item.getPolicyCode());
  }
}
