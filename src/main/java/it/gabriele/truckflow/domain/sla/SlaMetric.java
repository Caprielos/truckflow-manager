package it.gabriele.truckflow.domain.sla;

/** Metrica controllata da uno SLA contrattuale. */
public enum SlaMetric {
  PICKUP_ON_TIME,
  DELIVERY_ON_TIME,
  POD_AVAILABLE_ON_TIME,
  DOCUMENT_COMPLETENESS,
  TEMPERATURE_RANGE_COMPLIANCE,
  TRACKING_UPDATE_FREQUENCY,
  CLAIM_RESPONSE_TIME
}
