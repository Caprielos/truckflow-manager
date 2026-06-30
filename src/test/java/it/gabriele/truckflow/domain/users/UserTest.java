package it.gabriele.truckflow.domain.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void createsDomainUserWithRolesPermissionsProfileAndPreferences() {
    User user = createUser(UserRole.DISPATCHER, UserPermission.VIEW_TRIPS);

    assertTrue(user.isActive());
    assertTrue(user.canLogin());
    assertTrue(user.hasRole(UserRole.DISPATCHER));
    assertTrue(user.hasPermission(UserPermission.VIEW_TRIPS));
    assertFalse(user.hasPermission(UserPermission.MANAGE_TRIPS));
    assertEquals("Mario Rossi", user.profile().fullName());
    assertEquals("en", user.preferences().language());
  }

  @Test
  void normalizesUsername() {
    Username username = new Username("  Dispatcher.One  ");

    assertEquals("dispatcher.one", username.value());
  }

  @Test
  void grantsAndRevokesPermissionsSafely() {
    User user = createUser(UserRole.MANAGER);

    user.grantPermission(UserPermission.VIEW_REPORTS, "admin");
    assertTrue(user.hasPermission(UserPermission.VIEW_REPORTS));

    user.revokePermission(UserPermission.VIEW_REPORTS, "admin");
    assertFalse(user.hasPermission(UserPermission.VIEW_REPORTS));
  }

  @Test
  void checksMultipleRoles() {
    User user = createUser(Set.of(UserRole.ADMIN, UserRole.MANAGER), UserPermissions.empty());

    assertTrue(user.hasAnyRole(Set.of(UserRole.MANAGER, UserRole.DISPATCHER)));
    assertTrue(user.hasAllRoles(Set.of(UserRole.ADMIN, UserRole.MANAGER)));
    assertFalse(user.hasAllRoles(Set.of(UserRole.ADMIN, UserRole.DISPATCHER)));
  }

  @Test
  void changesAccountStatus() {
    User user = createUser(UserRole.MECHANIC);

    user.suspend("admin");
    assertTrue(user.isSuspended());
    assertFalse(user.canLogin());

    user.activate("admin");
    assertTrue(user.isActive());

    user.disable("admin");
    assertTrue(user.isDisabled());
    assertFalse(user.canLogin());
  }

  @Test
  void doesNotRemoveLastRole() {
    User user = createUser(UserRole.DISPATCHER);

    assertThrows(IllegalStateException.class, () -> user.removeRole(UserRole.DISPATCHER, "admin"));
  }

  @Test
  void disabledUserCannotBeSuspendedOrActivatedWithStandardMethod() {
    User user = createUser(UserRole.DISPATCHER);

    user.disable("admin");

    assertThrows(IllegalStateException.class, () -> user.suspend("admin"));
    assertThrows(IllegalStateException.class, () -> user.activate("admin"));

    user.reactivateDisabled("admin");
    assertTrue(user.isActive());
  }

  @Test
  void updatesMetadataWhenUserChanges() {
    User user = createUser(UserRole.DISPATCHER);
    var previousUpdatedAt = user.metadata().updatedAt();

    user.updateNotes("Updated note", "admin");

    assertTrue(user.metadata().updatedAt().isAfter(previousUpdatedAt));
    assertEquals("admin", user.metadata().updatedBy());
  }

  @Test
  void validatesEmailWhenPresent() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new UserContact("invalid-email", "", "+393331112233"));
  }

  @Test
  void failedUsernameChangeDoesNotMutateUser() {
    User user = createUser(UserRole.DISPATCHER);

    assertThrows(
        IllegalArgumentException.class, () -> user.changeUsername(new Username("new.user"), " "));

    assertEquals(new Username("mario.rossi"), user.username());
  }

  private static User createUser(UserRole role, UserPermission... permissions) {
    return createUser(Set.of(role), UserPermissions.of(permissions));
  }

  private static User createUser(Set<UserRole> roles, UserPermissions permissions) {
    return new User(
        UserId.random(),
        new Username("mario.rossi"),
        new UserPasswordHash("hashed-password"),
        roles,
        permissions,
        UserStatus.ACTIVE,
        new UserProfile(
            "Mario",
            "Rossi",
            new UserContact("mario.rossi@example.com", "", "+393331112233"),
            new UserAddress("Main Street", "10", "00100", "Rome", "RM", "Italy")),
        UserMetadata.createdNow("system"),
        UserPreferences.defaults(),
        "Test user");
  }
}
