package it.gabriele.truckflow.domain.payroll;

/** Unità di calcolo di una voce paga autista. */
public enum DriverPayUnit {
  PER_HOUR,
  PER_DAY,
  PER_MISSION,
  PERCENTAGE_OF_BASE,
  REIMBURSEMENT;

  public boolean usesWorkedQuantity() {
    return this == PER_HOUR || this == PER_DAY;
  }

  public boolean usesPercentage() {
    return this == PERCENTAGE_OF_BASE;
  }
}
