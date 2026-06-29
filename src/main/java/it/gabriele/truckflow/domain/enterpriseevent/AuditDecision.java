package it.gabriele.truckflow.domain.enterpriseevent;

/** Decisione audit automatica derivata da evento domain. */
public enum AuditDecision {
  DO_NOT_AUDIT,
  AUDIT_LIGHT,
  AUDIT_FULL,
  AUDIT_AND_ESCALATE
}
