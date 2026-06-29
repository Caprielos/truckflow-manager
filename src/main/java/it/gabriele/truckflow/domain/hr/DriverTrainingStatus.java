package it.gabriele.truckflow.domain.hr;

/** Stato di una formazione obbligatoria autista. */
public enum DriverTrainingStatus {
  PLANNED(false),
  COMPLETED(false),
  EXPIRED(true),
  CANCELLED(false);

  private final boolean requiresAttention;

  DriverTrainingStatus(boolean requiresAttention) {
    this.requiresAttention = requiresAttention;
  }

  public boolean requiresAttention() {
    return requiresAttention;
  }
}
