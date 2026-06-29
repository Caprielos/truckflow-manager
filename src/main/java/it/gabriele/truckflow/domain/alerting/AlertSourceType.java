package it.gabriele.truckflow.domain.alerting;

/** Origine funzionale di un alert. */
public enum AlertSourceType {
  DEADLINE,
  MISSION,
  DOCUMENT,
  DRIVER,
  VEHICLE,
  FUEL,
  TELEMATICS,
  SLA,
  CLAIM,
  COMPLIANCE,
  SYSTEM
}
