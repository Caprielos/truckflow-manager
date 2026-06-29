package it.gabriele.truckflow.infrastructure.memory.payroll;

import it.gabriele.truckflow.application.port.out.payroll.DriverMissionWorkReportRepository;
import it.gabriele.truckflow.domain.payroll.DriverMissionWorkReport;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DriverMissionWorkReport. */
public final class InMemoryDriverMissionWorkReportRepository
    extends InMemoryRepository<DriverMissionWorkReport>
    implements DriverMissionWorkReportRepository {

  public InMemoryDriverMissionWorkReportRepository() {
    super(item -> item.getReportCode());
  }
}
