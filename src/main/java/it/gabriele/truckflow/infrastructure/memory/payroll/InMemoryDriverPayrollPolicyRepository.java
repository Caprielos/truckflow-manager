package it.gabriele.truckflow.infrastructure.memory.payroll;

import it.gabriele.truckflow.application.port.out.DriverPayrollPolicyRepository;
import it.gabriele.truckflow.domain.payroll.DriverPayrollPolicy;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DriverPayrollPolicy. */
public final class InMemoryDriverPayrollPolicyRepository
    extends InMemoryRepository<DriverPayrollPolicy> implements DriverPayrollPolicyRepository {

  public InMemoryDriverPayrollPolicyRepository() {
    super(item -> item.getPolicyCode());
  }
}
