package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DriverMissionPayrollRepository;
import it.gabriele.truckflow.domain.payroll.DriverMissionPayroll;

/** Repository in memoria per DriverMissionPayroll. */
public final class InMemoryDriverMissionPayrollRepository
    extends InMemoryRepository<DriverMissionPayroll> implements DriverMissionPayrollRepository {

  public InMemoryDriverMissionPayrollRepository() {
    super(item -> item.getPayrollCode());
  }
}
