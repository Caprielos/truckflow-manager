package it.gabriele.truckflow.domain.document;

/** Stato di una versione documentale. */
public enum DocumentVersionStatus {
  DRAFT(false),
  CURRENT(true),
  SUPERSEDED(false),
  ARCHIVED(false);

  private final boolean current;

  DocumentVersionStatus(boolean current) {
    this.current = current;
  }

  public boolean isCurrent() {
    return current;
  }
}
