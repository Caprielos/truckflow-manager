package it.gabriele.truckflow.domain.carrierliability;

/** Evento che può generare responsabilità del vettore. */
public enum LiabilityEventType {
  CARGO_DAMAGE,
  CARGO_LOSS,
  THEFT,
  DELAY,
  TEMPERATURE_EXCURSION,
  CONTAMINATION,
  ADR_SPILL,
  ROAD_ACCIDENT,
  DOCUMENT_ERROR,
  WRONG_DELIVERY,
  MISSING_POD
}
