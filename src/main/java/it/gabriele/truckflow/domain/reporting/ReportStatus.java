package it.gabriele.truckflow.domain.reporting;

/** Stato di un report generato. */
public enum ReportStatus {
  DRAFT(false, false),
  GENERATED(false, true),
  PUBLISHED(false, true),
  ARCHIVED(true, true),
  FAILED(true, false);

  private final boolean terminal;
  private final boolean readable;

  ReportStatus(boolean terminal, boolean readable) {
    this.terminal = terminal;
    this.readable = readable;
  }

  public boolean isTerminal() {
    return terminal;
  }

  public boolean isReadable() {
    return readable;
  }
}
