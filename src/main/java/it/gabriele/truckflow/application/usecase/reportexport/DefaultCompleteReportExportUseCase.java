package it.gabriele.truckflow.application.usecase.reportexport;

import it.gabriele.truckflow.application.port.in.reportexport.CompleteReportExportUseCase;
import it.gabriele.truckflow.application.port.out.reportexport.ReportExportJobRepository;
import it.gabriele.truckflow.domain.reportexport.ReportExportJob;
import it.gabriele.truckflow.domain.reportexport.ReportExportStatus;

/** Implementazione default di CompleteReportExportUseCase. */
public final class DefaultCompleteReportExportUseCase implements CompleteReportExportUseCase {

  private final ReportExportJobRepository jobRepository;

  public DefaultCompleteReportExportUseCase(ReportExportJobRepository jobRepository) {
    this.jobRepository = jobRepository;
  }

  @Override
  public ReportExportJob handle(Command command) {
    ReportExportJob current = jobRepository.getRequired(command.jobCode(), "Job export report");
    ReportExportJob completed =
        new ReportExportJob(
            current.jobCode(),
            current.reportType(),
            current.format(),
            ReportExportStatus.COMPLETED,
            current.requestedAt(),
            current.requestedBy(),
            current.filters(),
            command.outputReference());
    jobRepository.save(completed);
    return completed;
  }
}
