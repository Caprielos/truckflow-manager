package it.gabriele.truckflow.domain.securitypolicy;

/** Regole per autorizzazione, MFA e audit reason. */
public final class SecurityPolicyRules {

  private SecurityPolicyRules() {}

  public static boolean canPerform(
      EnterpriseAccessPolicy policy, String action, boolean mfaPassed) {
    if (policy == null) {
      throw new IllegalArgumentException("La policy è obbligatoria.");
    }
    if (policy.requiresMfa() && !mfaPassed) {
      return false;
    }
    return policy.allows(action);
  }

  public static boolean requiresAuditReason(EnterpriseAccessPolicy policy, String action) {
    if (policy == null) {
      throw new IllegalArgumentException("La policy è obbligatoria.");
    }
    String normalizedAction = action == null ? "" : action.trim().toUpperCase();
    return policy.requiresAuditReason()
        || normalizedAction.contains("APPROVE")
        || normalizedAction.contains("DELETE")
        || normalizedAction.contains("WAIVE");
  }
}
