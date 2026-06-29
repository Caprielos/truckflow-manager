package it.gabriele.truckflow.domain.contract;

/** Unità con cui una tariffa viene applicata al cliente. */
public enum ChargeUnit {
  FIXED_AMOUNT,
  PER_KILOMETER,
  PER_STOP,
  PER_PALLET,
  PER_TON,
  PER_HOUR,
  PER_DAY,
  PER_SHIPMENT,
  PER_WAITING_HOUR,
  PERCENTAGE_OF_BASE_FEE;

  public boolean isPercentageBased() {
    return this == PERCENTAGE_OF_BASE_FEE;
  }
}
