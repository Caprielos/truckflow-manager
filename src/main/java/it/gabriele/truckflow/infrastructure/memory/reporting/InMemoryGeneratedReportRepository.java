package it.gabriele.truckflow.infrastructure.memory.reporting;

import it.gabriele.truckflow.application.port.out.GeneratedReportRepository;
import it.gabriele.truckflow.domain.reporting.GeneratedReport;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per GeneratedReport. */
public final class InMemoryGeneratedReportRepository extends InMemoryRepository<GeneratedReport>
    implements GeneratedReportRepository {

  public InMemoryGeneratedReportRepository() {
    super(item -> item.getReportNumber());
  }
}
