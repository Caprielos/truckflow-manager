package it.gabriele.truckflow.domain.dataimport;

/** Stato di una riga importata. */
public enum ImportRecordStatus {
  RAW(false),
  VALIDATED(false),
  REJECTED(true),
  POSTED_TO_DOMAIN(true),
  DUPLICATE(true);

  private final boolean terminal;

  ImportRecordStatus(boolean terminal) {
    this.terminal = terminal;
  }

  public boolean isTerminal() {
    return terminal;
  }

  public boolean canBePosted() {
    return this == VALIDATED;
  }
}
