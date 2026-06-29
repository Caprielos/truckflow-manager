package it.gabriele.truckflow.domain.alerting;

/** Stato di lavorazione di un alert. */
public enum AlertStatus {
  OPEN(true),
  ACKNOWLEDGED(true),
  RESOLVED(false),
  DISMISSED(false);

  private final boolean active;

  AlertStatus(boolean active) {
    this.active = active;
  }

  public boolean isActive() {
    return active;
  }

  public boolean isTerminal() {
    return !active;
  }
}
