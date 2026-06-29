package it.gabriele.truckflow.domain.claim;

/** Stato di un reclamo. */
public enum ClaimStatus {
  OPEN(false),
  UNDER_REVIEW(false),
  ACCEPTED(false),
  SETTLED(true),
  REJECTED(true),
  CANCELLED(true);

  private final boolean terminal;

  ClaimStatus(boolean terminal) {
    this.terminal = terminal;
  }

  public boolean isTerminal() {
    return terminal;
  }
}
