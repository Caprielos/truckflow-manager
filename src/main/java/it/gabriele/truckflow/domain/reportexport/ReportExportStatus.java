package it.gabriele.truckflow.domain.reportexport;

/** Stato export report. */
public enum ReportExportStatus {
  REQUESTED,
  QUEUED,
  RUNNING,
  COMPLETED,
  FAILED,
  EXPIRED
}
