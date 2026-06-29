package it.gabriele.truckflow.domain.economics;

/** Ricavi che l'azienda può fatturare al cliente per una missione. */
public enum MissionRevenueType {
  BASE_TRANSPORT_FEE,
  FUEL_SURCHARGE,
  TOLL_REIMBURSEMENT,
  ADR_SURCHARGE,
  TEMPERATURE_CONTROL_SURCHARGE,
  WASTE_TRANSPORT_SURCHARGE,
  LIVE_ANIMAL_SURCHARGE,
  WAITING_TIME_FEE,
  HANDLING_FEE,
  STORAGE_FEE,
  PALLET_EXCHANGE_FEE,
  URGENCY_SURCHARGE,
  EXTRA_STOP_FEE,
  OTHER;

  public boolean isSurcharge() {
    return this != BASE_TRANSPORT_FEE && this != OTHER;
  }
}
