package it.gabriele.truckflow.domain.reporting;

/**
 * Formato di esportazione del report.
 */
public enum ReportFormat {

    PDF(true, false),
    CSV(true, true),
    XLSX(true, true),
    JSON(false, true),
    HTML(false, false);

    private final boolean downloadable;
    private final boolean machineReadable;

    ReportFormat(boolean downloadable, boolean machineReadable) {
        this.downloadable = downloadable;
        this.machineReadable = machineReadable;
    }

    public boolean isDownloadable() {
        return downloadable;
    }

    public boolean isMachineReadable() {
        return machineReadable;
    }
}
