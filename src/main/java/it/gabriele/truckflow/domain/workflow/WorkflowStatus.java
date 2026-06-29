package it.gabriele.truckflow.domain.workflow;

/** Stato di una istanza workflow. */
public enum WorkflowStatus {
  NOT_STARTED(false, false),
  IN_PROGRESS(true, false),
  WAITING_APPROVAL(true, false),
  COMPLETED(false, true),
  CANCELLED(false, true);

  private final boolean active;
  private final boolean terminal;

  WorkflowStatus(boolean active, boolean terminal) {
    this.active = active;
    this.terminal = terminal;
  }

  public boolean isActive() {
    return active;
  }

  public boolean isTerminal() {
    return terminal;
  }
}
