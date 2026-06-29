package it.gabriele.truckflow.application.port.out.deadline;

/** Stato sintetico restituito dal gateway verso il servizio scadenze. */
public enum DeadlineGatewayStatus {
  NOT_APPLICABLE,
  CONFIGURATION_MISSING,
  OK,
  DUE_SOON,
  DUE_NOW,
  OVERDUE,
  BLOCKING,
  SUSPENDED,
  MANUAL_REVIEW_REQUIRED
}
