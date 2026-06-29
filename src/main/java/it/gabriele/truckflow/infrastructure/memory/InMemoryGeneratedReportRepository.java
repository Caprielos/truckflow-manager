package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.GeneratedReportRepository;
import it.gabriele.truckflow.domain.reporting.GeneratedReport;

/** Repository in memoria per GeneratedReport. */
public final class InMemoryGeneratedReportRepository extends InMemoryRepository<GeneratedReport> implements GeneratedReportRepository {

    public InMemoryGeneratedReportRepository() {
        super(item -> item.getReportNumber());
    }
}
