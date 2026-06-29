package it.gabriele.truckflow.domain.identity;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** Testa IdentityRules. */
class IdentityRulesTest {

  @Test
  void shouldCheckLoginRules() {
    assertTrue(IdentityRules.canLogin(adminAccount()));
    assertFalse(IdentityRules.canLogin(lockedAccount()));
    assertFalse(IdentityRules.canLogin(invitedAccount()));
  }

  @Test
  void shouldCheckLifecycleRules() {
    UserAccount invited = invitedAccount();
    UserAccount active = adminAccount();
    UserAccount locked = lockedAccount();
    UserAccount disabled = disabledAccount();
    UserAccount deleted = disabled.delete();

    assertTrue(IdentityRules.canBeActivated(invited));
    assertTrue(IdentityRules.canBeActivated(locked));
    assertTrue(IdentityRules.canBeActivated(disabled));
    assertFalse(IdentityRules.canBeActivated(active));
    assertFalse(IdentityRules.canBeActivated(deleted));

    assertTrue(IdentityRules.canBeLocked(active));
    assertFalse(IdentityRules.canBeLocked(locked));

    assertTrue(IdentityRules.canBeDisabled(invited));
    assertTrue(IdentityRules.canBeDisabled(active));
    assertTrue(IdentityRules.canBeDisabled(locked));
    assertFalse(IdentityRules.canBeDisabled(deleted));

    assertTrue(IdentityRules.canBeDeleted(active));
    assertFalse(IdentityRules.canBeDeleted(deleted));
  }

  @Test
  void shouldCheckAdministrativeAccess() {
    UserAccount admin = adminAccount();
    UserAccount viewer = viewerAccount();

    assertTrue(IdentityRules.canManageUsers(admin));
    assertTrue(IdentityRules.canViewAudit(admin));
    assertTrue(IdentityRules.canManageConfiguration(admin));

    assertFalse(IdentityRules.canManageUsers(viewer));
    assertFalse(IdentityRules.canViewAudit(viewer));
    assertFalse(IdentityRules.canManageConfiguration(viewer));
  }

  @Test
  void shouldCheckBackOfficeDriverAndCustomerAccess() {
    assertTrue(IdentityRules.canAccessBackOffice(dispatcherAccount()));
    assertFalse(IdentityRules.canAccessBackOffice(customerAccount()));

    assertTrue(IdentityRules.canAccessCustomerPortal(customerAccount()));
    assertFalse(IdentityRules.canAccessCustomerPortal(dispatcherAccount()));

    assertTrue(IdentityRules.canAccessDriverPortal(driverAccount()));
    assertFalse(IdentityRules.canAccessDriverPortal(dispatcherAccount()));
  }

  @Test
  void shouldNotAllowAccessForLockedAccount() {
    UserAccount locked = lockedAccount();

    assertFalse(IdentityRules.canAccessBackOffice(locked));
    assertFalse(IdentityRules.canManageUsers(locked));
    assertFalse(IdentityRules.canPerformSensitiveAction(locked, UserPermission.MANAGE_USERS));
  }

  @Test
  void shouldCheckSensitiveActionsAndStrongAuthentication() {
    UserAccount admin = adminAccount();
    UserAccount viewer = viewerAccount();

    assertTrue(IdentityRules.canPerformSensitiveAction(admin, UserPermission.MANAGE_USERS));
    assertFalse(IdentityRules.canPerformSensitiveAction(viewer, UserPermission.MANAGE_USERS));
    assertFalse(IdentityRules.canPerformSensitiveAction(admin, UserPermission.VIEW_SHIPMENTS));

    assertTrue(IdentityRules.requiresStrongAuthentication(admin));
    assertFalse(IdentityRules.requiresStrongAuthentication(viewer));
  }

  @Test
  void shouldNotAllowNullValues() {
    UserAccount account = adminAccount();

    assertThrows(IllegalArgumentException.class, () -> IdentityRules.canLogin(null));
    assertThrows(IllegalArgumentException.class, () -> IdentityRules.canBeActivated(null));
    assertThrows(IllegalArgumentException.class, () -> IdentityRules.canBeLocked(null));
    assertThrows(IllegalArgumentException.class, () -> IdentityRules.canBeDisabled(null));
    assertThrows(IllegalArgumentException.class, () -> IdentityRules.canBeDeleted(null));
    assertThrows(IllegalArgumentException.class, () -> IdentityRules.canManageUsers(null));
    assertThrows(IllegalArgumentException.class, () -> IdentityRules.canViewAudit(null));
    assertThrows(IllegalArgumentException.class, () -> IdentityRules.canManageConfiguration(null));
    assertThrows(IllegalArgumentException.class, () -> IdentityRules.canAccessBackOffice(null));
    assertThrows(IllegalArgumentException.class, () -> IdentityRules.canAccessDriverPortal(null));
    assertThrows(IllegalArgumentException.class, () -> IdentityRules.canAccessCustomerPortal(null));
    assertThrows(
        IllegalArgumentException.class,
        () -> IdentityRules.canPerformSensitiveAction(null, UserPermission.MANAGE_USERS));
    assertThrows(
        IllegalArgumentException.class,
        () -> IdentityRules.canPerformSensitiveAction(account, null));
    assertThrows(
        IllegalArgumentException.class, () -> IdentityRules.requiresStrongAuthentication(null));
  }

  private static UserAccount adminAccount() {
    return UserAccount.active(
        "USR-ADMIN",
        "admin@example.com",
        "Admin User",
        Set.of(UserRole.ADMIN),
        Set.of(
            UserPermission.MANAGE_USERS,
            UserPermission.VIEW_AUDIT,
            UserPermission.MANAGE_CONFIGURATION),
        Notes.empty());
  }

  private static UserAccount dispatcherAccount() {
    return UserAccount.active(
        "USR-DISPATCHER",
        "dispatcher@example.com",
        "Dispatcher User",
        Set.of(UserRole.DISPATCHER),
        Set.of(UserPermission.VIEW_SHIPMENTS, UserPermission.MANAGE_OPERATIONS),
        Notes.empty());
  }

  private static UserAccount viewerAccount() {
    return UserAccount.active(
        "USR-VIEWER",
        "viewer@example.com",
        "Viewer User",
        Set.of(UserRole.VIEWER),
        Set.of(UserPermission.VIEW_SHIPMENTS),
        Notes.empty());
  }

  private static UserAccount customerAccount() {
    return UserAccount.active(
        "USR-CUSTOMER",
        "customer@example.com",
        "Customer User",
        Set.of(UserRole.CUSTOMER),
        Set.of(UserPermission.VIEW_SHIPMENTS),
        Notes.empty());
  }

  private static UserAccount driverAccount() {
    return UserAccount.active(
        "USR-DRIVER",
        "driver@example.com",
        "Driver User",
        Set.of(UserRole.DRIVER),
        Set.of(UserPermission.VIEW_SHIPMENTS),
        Notes.empty());
  }

  private static UserAccount lockedAccount() {
    return UserAccount.locked(
        "USR-LOCKED",
        "locked@example.com",
        "Locked User",
        Set.of(UserRole.DISPATCHER),
        Set.of(UserPermission.MANAGE_OPERATIONS),
        Notes.empty());
  }

  private static UserAccount invitedAccount() {
    return UserAccount.invited(
        "USR-INVITED",
        "invited@example.com",
        "Invited User",
        Set.of(UserRole.PLANNER),
        Set.of(UserPermission.VIEW_SHIPMENTS),
        Notes.empty());
  }

  private static UserAccount disabledAccount() {
    return UserAccount.disabled(
        "USR-DISABLED",
        "disabled@example.com",
        "Disabled User",
        Set.of(UserRole.PLANNER),
        Set.of(UserPermission.VIEW_SHIPMENTS),
        Notes.empty());
  }
}
