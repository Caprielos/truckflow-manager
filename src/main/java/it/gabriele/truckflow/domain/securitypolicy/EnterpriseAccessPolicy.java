package it.gabriele.truckflow.domain.securitypolicy;

import java.util.Set;

/** Policy di accesso granulare per reparto, risorsa e livello. */
public record EnterpriseAccessPolicy(
    String policyCode,
    EnterpriseDepartment department,
    ProtectedResourceType resourceType,
    DataAccessLevel accessLevel,
    Set<String> allowedActions,
    boolean requiresMfa,
    boolean requiresAuditReason) {

  public EnterpriseAccessPolicy {
    policyCode = normalize(policyCode, "Il codice policy è obbligatorio.");
    if (department == null || resourceType == null || accessLevel == null) {
      throw new IllegalArgumentException("Reparto, risorsa e livello accesso sono obbligatori.");
    }
    allowedActions = allowedActions == null ? Set.of() : Set.copyOf(allowedActions);
  }

  public boolean allows(String action) {
    if (action == null || action.trim().isEmpty()) {
      throw new IllegalArgumentException("L'azione è obbligatoria.");
    }
    return accessLevel == DataAccessLevel.ADMIN
        || allowedActions.contains(action.trim().toUpperCase());
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
