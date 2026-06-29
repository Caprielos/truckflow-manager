package it.gabriele.truckflow.domain.alerting;

/** Tipo di alert generato da processi enterprise. */
public enum AlertType {
  DEADLINE_DUE_SOON,
  DEADLINE_OVERDUE,
  MISSION_DELAYED,
  DOCUMENT_MISSING,
  DRIVER_HOURS_RISK,
  FUEL_ANOMALY,
  TELEMATICS_ANOMALY,
  SLA_VIOLATION,
  VEHICLE_DOWNTIME,
  CLAIM_ESCALATION,
  COMPLIANCE_BLOCKED,
  OTHER
}
