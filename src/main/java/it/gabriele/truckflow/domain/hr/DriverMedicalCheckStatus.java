package it.gabriele.truckflow.domain.hr;

/** Esito o stato di visita medica autista. */
public enum DriverMedicalCheckStatus {
  SCHEDULED(false, false),
  FIT(false, true),
  FIT_WITH_LIMITATIONS(true, true),
  NOT_FIT(true, false),
  EXPIRED(true, false),
  CANCELLED(false, false);

  private final boolean requiresAttention;
  private final boolean canDrive;

  DriverMedicalCheckStatus(boolean requiresAttention, boolean canDrive) {
    this.requiresAttention = requiresAttention;
    this.canDrive = canDrive;
  }

  public boolean requiresAttention() {
    return requiresAttention;
  }

  public boolean canDrive() {
    return canDrive;
  }
}
