package it.gabriele.truckflow.domain.claim;

/** Gravità del reclamo. */
public enum ClaimSeverity {
  LOW(1, false),
  MEDIUM(2, false),
  HIGH(3, true),
  CRITICAL(4, true);

  private final int level;
  private final boolean urgent;

  ClaimSeverity(int level, boolean urgent) {
    this.level = level;
    this.urgent = urgent;
  }

  public int getLevel() {
    return level;
  }

  public boolean isUrgent() {
    return urgent;
  }

  public boolean isAtLeast(ClaimSeverity other) {
    if (other == null) {
      throw new IllegalArgumentException("La gravità da confrontare è obbligatoria.");
    }

    return level >= other.level;
  }
}
