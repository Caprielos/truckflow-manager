package it.gabriele.truckflow.domain.document;

/** Stato di un documento di trasporto. */
public enum DocumentStatus {
  DRAFT(false, false),
  REQUESTED(false, false),
  RECEIVED(false, false),
  VERIFIED(false, true),
  REJECTED(true, false),
  EXPIRED(true, false);

  private final boolean terminal;
  private final boolean usableForOperation;

  DocumentStatus(boolean terminal, boolean usableForOperation) {
    this.terminal = terminal;
    this.usableForOperation = usableForOperation;
  }

  public boolean isTerminal() {
    return terminal;
  }

  public boolean isUsableForOperation() {
    return usableForOperation;
  }
}
