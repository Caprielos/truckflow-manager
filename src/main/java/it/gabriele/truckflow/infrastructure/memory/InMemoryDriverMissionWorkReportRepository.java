package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DriverMissionWorkReportRepository;
import it.gabriele.truckflow.domain.payroll.DriverMissionWorkReport;

/** Repository in memoria per DriverMissionWorkReport. */
public final class InMemoryDriverMissionWorkReportRepository extends InMemoryRepository<DriverMissionWorkReport> implements DriverMissionWorkReportRepository {

    public InMemoryDriverMissionWorkReportRepository() {
        super(item -> item.getReportCode());
    }
}
