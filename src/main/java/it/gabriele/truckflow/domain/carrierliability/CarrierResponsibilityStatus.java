package it.gabriele.truckflow.domain.carrierliability;

/** Stato valutazione responsabilità vettore. */
public enum CarrierResponsibilityStatus {
  UNDER_REVIEW,
  CARRIER_RESPONSIBLE,
  CUSTOMER_RESPONSIBLE,
  THIRD_PARTY_RESPONSIBLE,
  FORCE_MAJEURE,
  WAIVED,
  CLOSED
}
