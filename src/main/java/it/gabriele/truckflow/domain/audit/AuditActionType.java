package it.gabriele.truckflow.domain.audit;

/** Tipo di azione registrata nell'audit trail. */
public enum AuditActionType {
  CREATED(true, false, false),
  UPDATED(true, false, false),
  STATUS_CHANGED(true, false, false),
  ASSIGNED(true, false, false),
  CANCELLED(true, false, false),
  DELETED(true, false, false),

  DOCUMENT_VERIFIED(true, false, false),
  PAYMENT_REGISTERED(true, false, true),
  CLAIM_SETTLED(true, false, true),
  EXTERNAL_ESTIMATE_IMPORTED(true, false, false),

  LOGIN(false, true, false),
  LOGIN_FAILED(false, true, false),
  PERMISSION_DENIED(false, true, false);

  private final boolean dataChange;
  private final boolean securitySensitive;
  private final boolean financialImpact;

  AuditActionType(boolean dataChange, boolean securitySensitive, boolean financialImpact) {
    this.dataChange = dataChange;
    this.securitySensitive = securitySensitive;
    this.financialImpact = financialImpact;
  }

  public boolean isDataChange() {
    return dataChange;
  }

  public boolean isSecuritySensitive() {
    return securitySensitive;
  }

  public boolean hasFinancialImpact() {
    return financialImpact;
  }
}
