package it.gabriele.truckflow.domain.audit;

/** Gravità di un evento audit. */
public enum AuditSeverity {
  INFO(1, false),
  WARNING(2, true),
  ERROR(3, true),
  CRITICAL(4, true);

  private final int level;
  private final boolean requiresReview;

  AuditSeverity(int level, boolean requiresReview) {
    this.level = level;
    this.requiresReview = requiresReview;
  }

  public int getLevel() {
    return level;
  }

  public boolean requiresReview() {
    return requiresReview;
  }

  public boolean isAtLeast(AuditSeverity other) {
    if (other == null) {
      throw new IllegalArgumentException("La gravità audit da confrontare è obbligatoria.");
    }

    return level >= other.level;
  }
}
