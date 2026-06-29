package it.gabriele.truckflow.domain.reportexport;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ReportExportEnterpriseTest {

  @Test
  void shouldAllowDownloadOnlyForCompletedReportWithOutput() {
    ReportExportJob job =
        new ReportExportJob(
            "job-001",
            ReportExportType.OPERATIONAL_KPI,
            ReportExportFormat.XLSX,
            ReportExportStatus.COMPLETED,
            LocalDateTime.of(2026, 6, 29, 20, 0),
            "USR-001",
            Set.of("period=2026-06"),
            "s3://reports/operational-kpi.xlsx");

    assertTrue(ReportExportRules.canDownload(job));
  }
}
