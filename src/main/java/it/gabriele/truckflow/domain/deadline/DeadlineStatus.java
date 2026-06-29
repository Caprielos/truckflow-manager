package it.gabriele.truckflow.domain.deadline;

/** Stato operativo di una scadenza enterprise. */
public enum DeadlineStatus {
  PLANNED(false, false),
  DUE_SOON(true, false),
  OVERDUE(true, true),
  COMPLETED(false, false),
  WAIVED(false, false),
  CANCELLED(false, false);

  private final boolean requiresAttention;
  private final boolean expired;

  DeadlineStatus(boolean requiresAttention, boolean expired) {
    this.requiresAttention = requiresAttention;
    this.expired = expired;
  }

  public boolean requiresAttention() {
    return requiresAttention;
  }

  public boolean isExpired() {
    return expired;
  }

  public boolean isTerminal() {
    return this == COMPLETED || this == WAIVED || this == CANCELLED;
  }
}
