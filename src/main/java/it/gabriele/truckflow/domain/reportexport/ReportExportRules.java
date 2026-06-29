package it.gabriele.truckflow.domain.reportexport;

/** Regole per export report enterprise. */
public final class ReportExportRules {

  private ReportExportRules() {}

  public static boolean canDownload(ReportExportJob job) {
    if (job == null) {
      throw new IllegalArgumentException("Il job report è obbligatorio.");
    }
    return job.status() == ReportExportStatus.COMPLETED && job.hasOutput();
  }

  public static boolean requiresRetry(ReportExportJob job) {
    if (job == null) {
      throw new IllegalArgumentException("Il job report è obbligatorio.");
    }
    return job.status() == ReportExportStatus.FAILED;
  }
}
