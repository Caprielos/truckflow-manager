package it.gabriele.truckflow.domain.identity;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ExpandedIdentityPermissionsTest {

  @Test
  void shouldProtectCostsPayrollInventoryAndExternalImportsWithPermissions() {
    UserAccount accountant =
        UserAccount.active(
            "USR-ACC",
            "accounting@truckflow.local",
            "Accounting",
            Set.of(UserRole.ACCOUNTING),
            Set.of(UserPermission.VIEW_ECONOMICS, UserPermission.MANAGE_ECONOMICS),
            Notes.empty());

    UserAccount viewer =
        UserAccount.active(
            "USR-VIEW",
            "viewer@truckflow.local",
            "Viewer",
            Set.of(UserRole.VIEWER),
            Set.of(UserPermission.VIEW_REPORTS),
            Notes.empty());

    UserAccount warehouse =
        UserAccount.active(
            "USR-WH",
            "warehouse@truckflow.local",
            "Warehouse",
            Set.of(UserRole.MAINTENANCE),
            Set.of(UserPermission.MANAGE_INVENTORY, UserPermission.IMPORT_EXTERNAL_DATA),
            Notes.empty());

    assertTrue(IdentityRules.canViewEconomics(accountant));
    assertTrue(IdentityRules.canManageEconomics(accountant));
    assertFalse(IdentityRules.canViewPayroll(accountant));
    assertFalse(IdentityRules.canManageEconomics(viewer));
    assertTrue(IdentityRules.canManageInventory(warehouse));
    assertTrue(IdentityRules.canImportExternalData(warehouse));
  }
}
