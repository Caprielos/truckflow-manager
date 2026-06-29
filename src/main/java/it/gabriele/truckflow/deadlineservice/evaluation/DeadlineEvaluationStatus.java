package it.gabriele.truckflow.deadlineservice.evaluation;

/** Stato calcolato dal futuro microservizio scadenze per un elemento gestito. */
public enum DeadlineEvaluationStatus {
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
