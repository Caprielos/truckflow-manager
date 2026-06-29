package it.gabriele.truckflow.domain.alerting;

/** Gravità di un alert operativo. */
public enum AlertSeverity {
  INFO(false, false),
  WARNING(true, false),
  HIGH(true, true),
  CRITICAL(true, true);

  private final boolean requiresAttention;
  private final boolean escalationRequired;

  AlertSeverity(boolean requiresAttention, boolean escalationRequired) {
    this.requiresAttention = requiresAttention;
    this.escalationRequired = escalationRequired;
  }

  public boolean requiresAttention() {
    return requiresAttention;
  }

  public boolean requiresEscalation() {
    return escalationRequired;
  }
}
