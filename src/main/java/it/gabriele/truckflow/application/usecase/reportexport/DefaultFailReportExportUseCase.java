package it.gabriele.truckflow.application.usecase.reportexport;

import it.gabriele.truckflow.application.port.in.reportexport.FailReportExportUseCase;
import it.gabriele.truckflow.application.port.out.ReportExportJobRepository;
import it.gabriele.truckflow.domain.reportexport.ReportExportJob;
import it.gabriele.truckflow.domain.reportexport.ReportExportStatus;

/** Implementazione default di FailReportExportUseCase. */
public final class DefaultFailReportExportUseCase implements FailReportExportUseCase {

  private final ReportExportJobRepository jobRepository;

  public DefaultFailReportExportUseCase(ReportExportJobRepository jobRepository) {
    this.jobRepository = jobRepository;
  }

  @Override
  public ReportExportJob handle(Command command) {
    ReportExportJob current = jobRepository.getRequired(command.jobCode(), "Job export report");
    ReportExportJob failed =
        new ReportExportJob(
            current.jobCode(),
            current.reportType(),
            current.format(),
            ReportExportStatus.FAILED,
            current.requestedAt(),
            current.requestedBy(),
            current.filters(),
            current.outputReference());
    jobRepository.save(failed);
    return failed;
  }
}
