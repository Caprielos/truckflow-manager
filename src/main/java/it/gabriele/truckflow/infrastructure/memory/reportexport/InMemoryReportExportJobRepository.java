package it.gabriele.truckflow.infrastructure.memory.reportexport;

import it.gabriele.truckflow.application.port.out.reportexport.ReportExportJobRepository;
import it.gabriele.truckflow.domain.reportexport.ReportExportJob;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per ReportExportJob. */
public final class InMemoryReportExportJobRepository extends InMemoryRepository<ReportExportJob>
    implements ReportExportJobRepository {

  public InMemoryReportExportJobRepository() {
    super(job -> job.jobCode());
  }
}
