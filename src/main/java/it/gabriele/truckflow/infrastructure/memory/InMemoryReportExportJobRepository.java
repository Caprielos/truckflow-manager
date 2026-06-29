package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.ReportExportJobRepository;
import it.gabriele.truckflow.domain.reportexport.ReportExportJob;

/** Repository in memoria per ReportExportJob. */
public final class InMemoryReportExportJobRepository extends InMemoryRepository<ReportExportJob>
    implements ReportExportJobRepository {

  public InMemoryReportExportJobRepository() {
    super(job -> job.jobCode());
  }
}
