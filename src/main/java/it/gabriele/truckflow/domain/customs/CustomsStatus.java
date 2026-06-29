package it.gabriele.truckflow.domain.customs;

/** Stato di una dichiarazione doganale o pratica internazionale. */
public enum CustomsStatus {
  DRAFT(false, false),
  SUBMITTED(true, false),
  WAITING_INSPECTION(true, false),
  CLEARED(false, true),
  BLOCKED(true, false),
  CANCELLED(false, true);

  private final boolean pending;
  private final boolean terminal;

  CustomsStatus(boolean pending, boolean terminal) {
    this.pending = pending;
    this.terminal = terminal;
  }

  public boolean isPending() {
    return pending;
  }

  public boolean isTerminal() {
    return terminal;
  }
}
