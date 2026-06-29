package it.gabriele.truckflow.domain.dispatch;

/** Esito sintetico di un controllo dispatch. */
public enum DispatchReadinessStatus {
  READY,
  WARNING,
  BLOCKED;

  public boolean blocksAssignment() {
    return this == BLOCKED;
  }

  public boolean requiresManualReview() {
    return this == WARNING || this == BLOCKED;
  }
}
