package it.gabriele.truckflow.domain.sla;

/** Stato di uno SLA cliente o fornitore. */
public enum SlaStatus {
  DRAFT(false),
  ACTIVE(true),
  SUSPENDED(false),
  EXPIRED(false);

  private final boolean active;

  SlaStatus(boolean active) {
    this.active = active;
  }

  public boolean isActive() {
    return active;
  }
}
