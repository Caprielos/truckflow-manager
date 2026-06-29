package it.gabriele.truckflow.domain.quality;

/** Tipo di evento qualità o non conformità servizio. */
public enum QualityEventType {
  CUSTOMER_COMPLAINT,
  DAMAGED_CARGO,
  LATE_DELIVERY,
  MISSING_DOCUMENT,
  TEMPERATURE_EXCURSION,
  DRIVER_BEHAVIOR,
  VEHICLE_ISSUE,
  WAREHOUSE_ERROR,
  CORRECTIVE_ACTION,
  OTHER
}
