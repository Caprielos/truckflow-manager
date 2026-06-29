package it.gabriele.truckflow.application.usecase.reportexport;

import it.gabriele.truckflow.application.port.in.RequestReportExportUseCase;
import it.gabriele.truckflow.application.port.out.ReportExportJobRepository;
import it.gabriele.truckflow.domain.reportexport.ReportExportJob;
import java.util.Objects;

/** Implementazione default di RequestReportExportUseCase. */
public final class DefaultRequestReportExportUseCase implements RequestReportExportUseCase {

  private final ReportExportJobRepository repository;

  public DefaultRequestReportExportUseCase(ReportExportJobRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public ReportExportJob handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    ReportExportJob aggregate =
        Objects.requireNonNull(command.job(), "Il job export report è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
