package it.gabriele.truckflow.domain.integration;

/** Stato di una integrazione o di un run di import/export. */
public enum IntegrationStatus {
  CONFIGURED(false, false),
  ACTIVE(true, false),
  RUNNING(true, false),
  COMPLETED(false, true),
  COMPLETED_WITH_ERRORS(false, true),
  FAILED(false, true),
  DISABLED(false, false);

  private final boolean active;
  private final boolean terminal;

  IntegrationStatus(boolean active, boolean terminal) {
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
