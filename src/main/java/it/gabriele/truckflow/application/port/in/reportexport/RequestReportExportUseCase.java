package it.gabriele.truckflow.application.port.in.reportexport;

import it.gabriele.truckflow.domain.reportexport.ReportExportJob;

public interface RequestReportExportUseCase {
  ReportExportJob handle(Command command);

  record Command(ReportExportJob job) {}
}
