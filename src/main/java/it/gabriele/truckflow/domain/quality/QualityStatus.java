package it.gabriele.truckflow.domain.quality;

/** Stato di gestione di un evento qualità. */
public enum QualityStatus {
  OPEN(true),
  UNDER_REVIEW(true),
  CORRECTIVE_ACTION_ASSIGNED(true),
  CLOSED(false),
  CANCELLED(false);

  private final boolean active;

  QualityStatus(boolean active) {
    this.active = active;
  }

  public boolean isActive() {
    return active;
  }
}
