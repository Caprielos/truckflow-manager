package it.gabriele.truckflow.domain.deadline;

/** Gravità della scadenza per priorità operative e alert. */
public enum DeadlineSeverity {
  LOW(false),
  MEDIUM(false),
  HIGH(true),
  CRITICAL(true);

  private final boolean escalationRequired;

  DeadlineSeverity(boolean escalationRequired) {
    this.escalationRequired = escalationRequired;
  }

  public boolean requiresEscalation() {
    return escalationRequired;
  }
}
