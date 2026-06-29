package it.gabriele.truckflow.infrastructure.memory.payroll;

import it.gabriele.truckflow.application.port.out.payroll.DriverMissionPayrollRepository;
import it.gabriele.truckflow.domain.payroll.DriverMissionPayroll;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DriverMissionPayroll. */
public final class InMemoryDriverMissionPayrollRepository
    extends InMemoryRepository<DriverMissionPayroll> implements DriverMissionPayrollRepository {

  public InMemoryDriverMissionPayrollRepository() {
    super(item -> item.getPayrollCode());
  }
}
