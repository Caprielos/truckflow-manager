package it.gabriele.truckflow.application.port.in.reportexport;

import it.gabriele.truckflow.domain.reportexport.ReportExportJob;

public interface CompleteReportExportUseCase {
  ReportExportJob handle(Command command);

  record Command(String jobCode, String outputReference) {}
}
