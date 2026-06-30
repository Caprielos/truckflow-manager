package it.gabriele.truckflow.domain.users;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void createsDomainUserWithRolesAndPermissions() {
    User user =
        new User(
            null,
            "dispatcher.one",
            "hashed-password",
            Set.of(UserRole.DISPATCHER),
            UserPermissions.of(UserPermission.VIEW_TRIPS),
            UserStatus.ACTIVE,
            new UserContact("dispatcher@example.com", "", "+393331112233"),
            new UserAddress("Main Street", "10", "00100", "Rome", "RM", "Italy"),
            UserMetadata.createdNow("system"),
            UserPreferences.defaults(),
            "Main dispatcher user");

    assertTrue(user.isActive());
    assertTrue(user.hasRole(UserRole.DISPATCHER));
    assertTrue(user.hasPermission(UserPermission.VIEW_TRIPS));
    assertFalse(user.hasPermission(UserPermission.MANAGE_TRIPS));
  }

  @Test
  void grantsAndRevokesPermissionsSafely() {
    User user =
        new User(
            null,
            "manager.one",
            "hashed-password",
            Set.of(UserRole.MANAGER),
            UserPermissions.empty(),
            UserStatus.ACTIVE,
            new UserContact("manager@example.com", "", ""),
            new UserAddress("Main Street", "20", "00100", "Rome", "RM", "Italy"),
            UserMetadata.createdNow("system"),
            UserPreferences.defaults(),
            "");

    user.grantPermission(UserPermission.VIEW_REPORTS, "admin");
    assertTrue(user.hasPermission(UserPermission.VIEW_REPORTS));

    user.revokePermission(UserPermission.VIEW_REPORTS, "admin");
    assertFalse(user.hasPermission(UserPermission.VIEW_REPORTS));
  }

  @Test
  void changesAccountStatus() {
    User user =
        new User(
            null,
            "mechanic.one",
            "hashed-password",
            Set.of(UserRole.MECHANIC),
            UserPermissions.empty(),
            UserStatus.ACTIVE,
            new UserContact("mechanic@example.com", "", ""),
            new UserAddress("Main Street", "30", "00100", "Rome", "RM", "Italy"),
            UserMetadata.createdNow("system"),
            UserPreferences.defaults(),
            "");

    user.suspend("admin");
    assertFalse(user.isActive());

    user.activate("admin");
    assertTrue(user.isActive());

    user.disable("admin");
    assertFalse(user.isActive());
  }
}
