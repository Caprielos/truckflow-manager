package it.gabriele.truckflow.domain.securitypolicy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

class SecurityPolicyEnterpriseTest {

  @Test
  void shouldApplyGranularPermissionsMfaAndAuditReason() {
    EnterpriseAccessPolicy policy =
        new EnterpriseAccessPolicy(
            "pol-001",
            EnterpriseDepartment.ACCOUNTING,
            ProtectedResourceType.INVOICE,
            DataAccessLevel.APPROVE,
            Set.of("READ", "APPROVE_INVOICE", "WAIVE_PENALTY"),
            true,
            true);

    assertFalse(SecurityPolicyRules.canPerform(policy, "APPROVE_INVOICE", false));
    assertTrue(SecurityPolicyRules.canPerform(policy, "APPROVE_INVOICE", true));
    assertTrue(SecurityPolicyRules.requiresAuditReason(policy, "WAIVE_PENALTY"));
  }
}
