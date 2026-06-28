package it.gabriele.truckflow.domain.identity;

import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa UserAccount.
 */
class UserAccountTest {

    @Test
    void shouldCreateActiveUserAccount() {
        UserAccount account = adminAccount();

        assertEquals("USR-001", account.getAccountId());
        assertEquals("admin@example.com", account.getEmail());
        assertEquals("Admin User", account.getDisplayName());
        assertEquals(UserAccountStatus.ACTIVE, account.getStatus());
        assertTrue(account.isActive());
        assertTrue(account.canLogin());
        assertTrue(account.isAdmin());
        assertTrue(account.isBackOfficeUser());
        assertTrue(account.hasSensitivePermissions());
    }

    @Test
    void shouldNormalizeAccountIdEmailAndDisplayName() {
        UserAccount account = UserAccount.active(
                "  usr_001  ",
                "  ADMIN@EXAMPLE.COM  ",
                "  Admin User  ",
                Set.of(UserRole.ADMIN),
                Set.of(UserPermission.MANAGE_USERS),
                Notes.empty()
        );

        assertEquals("USR_001", account.getAccountId());
        assertEquals("admin@example.com", account.getEmail());
        assertEquals("Admin User", account.getDisplayName());
    }

    @Test
    void shouldRejectInvalidAccountId() {
        assertThrows(IllegalArgumentException.class, () -> UserAccount.active(
                null,
                "admin@example.com",
                "Admin User",
                Set.of(UserRole.ADMIN),
                Set.of(UserPermission.MANAGE_USERS),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> UserAccount.active(
                "USR 001",
                "admin@example.com",
                "Admin User",
                Set.of(UserRole.ADMIN),
                Set.of(UserPermission.MANAGE_USERS),
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> UserAccount.active(
                "USR-001",
                null,
                "Admin User",
                Set.of(UserRole.ADMIN),
                Set.of(UserPermission.MANAGE_USERS),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> UserAccount.active(
                "USR-001",
                "not-an-email",
                "Admin User",
                Set.of(UserRole.ADMIN),
                Set.of(UserPermission.MANAGE_USERS),
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullOrEmptyRoleSet() {
        assertThrows(IllegalArgumentException.class, () -> UserAccount.active(
                "USR-001",
                "admin@example.com",
                "Admin User",
                null,
                Set.of(UserPermission.MANAGE_USERS),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> UserAccount.active(
                "USR-001",
                "admin@example.com",
                "Admin User",
                Set.of(),
                Set.of(UserPermission.MANAGE_USERS),
                Notes.empty()
        ));

        Set<UserRole> rolesWithNull = new HashSet<>(Arrays.asList(UserRole.ADMIN, null));

        assertThrows(IllegalArgumentException.class, () -> UserAccount.active(
                "USR-001",
                "admin@example.com",
                "Admin User",
                rolesWithNull,
                Set.of(UserPermission.MANAGE_USERS),
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullPermissionSetOrNullPermission() {
        assertThrows(IllegalArgumentException.class, () -> UserAccount.active(
                "USR-001",
                "admin@example.com",
                "Admin User",
                Set.of(UserRole.ADMIN),
                null,
                Notes.empty()
        ));

        Set<UserPermission> permissionsWithNull = new HashSet<>(Arrays.asList(UserPermission.MANAGE_USERS, null));

        assertThrows(IllegalArgumentException.class, () -> UserAccount.active(
                "USR-001",
                "admin@example.com",
                "Admin User",
                Set.of(UserRole.ADMIN),
                permissionsWithNull,
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullDisplayNameOrNotes() {
        assertThrows(IllegalArgumentException.class, () -> UserAccount.active(
                "USR-001",
                "admin@example.com",
                null,
                Set.of(UserRole.ADMIN),
                Set.of(UserPermission.MANAGE_USERS),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> UserAccount.active(
                "USR-001",
                "admin@example.com",
                "Admin User",
                Set.of(UserRole.ADMIN),
                Set.of(UserPermission.MANAGE_USERS),
                null
        ));
    }

    @Test
    void shouldMoveThroughLifecycle() {
        UserAccount invited = UserAccount.invited(
                "USR-002",
                "planner@example.com",
                "Planner User",
                Set.of(UserRole.PLANNER),
                Set.of(UserPermission.VIEW_SHIPMENTS),
                Notes.empty()
        );

        UserAccount active = invited.activate();
        UserAccount locked = active.lock();
        UserAccount reactivated = locked.activate();
        UserAccount disabled = reactivated.disable();
        UserAccount deleted = disabled.delete();

        assertTrue(active.isActive());
        assertTrue(locked.isLocked());
        assertTrue(reactivated.isActive());
        assertTrue(disabled.isDisabled());
        assertTrue(deleted.isDeleted());
        assertFalse(deleted.canLogin());
    }

    @Test
    void shouldNotAllowInvalidLifecycleTransitions() {
        UserAccount active = adminAccount();

        assertThrows(IllegalStateException.class, active::activate);

        UserAccount locked = active.lock();

        assertThrows(IllegalStateException.class, locked::lock);

        UserAccount deleted = locked.delete();

        assertThrows(IllegalStateException.class, deleted::activate);
        assertThrows(IllegalStateException.class, deleted::lock);
        assertThrows(IllegalStateException.class, deleted::disable);
        assertThrows(IllegalStateException.class, deleted::delete);
    }

    @Test
    void shouldCheckRolesAndPermissions() {
        UserAccount account = adminAccount();

        assertTrue(account.hasRole(UserRole.ADMIN));
        assertTrue(account.hasAnyRole(Set.of(UserRole.ADMIN, UserRole.DISPATCHER)));
        assertFalse(account.hasRole(UserRole.CUSTOMER));

        assertTrue(account.hasPermission(UserPermission.MANAGE_USERS));
        assertTrue(account.hasAnyPermission(Set.of(UserPermission.MANAGE_USERS, UserPermission.VIEW_REPORTS)));
        assertFalse(account.hasPermission(UserPermission.MANAGE_FLEET));

        assertThrows(IllegalArgumentException.class, () -> account.hasRole(null));
        assertThrows(IllegalArgumentException.class, () -> account.hasPermission(null));
        assertThrows(IllegalArgumentException.class, () -> account.hasAnyRole(null));
        assertThrows(IllegalArgumentException.class, () -> account.hasAnyPermission(null));
    }

    @Test
    void shouldDetectPortalUsers() {
        UserAccount driver = UserAccount.active(
                "USR-DRIVER",
                "driver@example.com",
                "Driver User",
                Set.of(UserRole.DRIVER),
                Set.of(UserPermission.VIEW_SHIPMENTS),
                Notes.empty()
        );

        UserAccount customer = UserAccount.active(
                "USR-CUSTOMER",
                "customer@example.com",
                "Customer User",
                Set.of(UserRole.CUSTOMER),
                Set.of(UserPermission.VIEW_SHIPMENTS),
                Notes.empty()
        );

        assertTrue(driver.isDriverPortalUser());
        assertFalse(driver.isBackOfficeUser());

        assertTrue(customer.isCustomerPortalUser());
        assertFalse(customer.isBackOfficeUser());
    }

    @Test
    void shouldDetectNotes() {
        UserAccount account = UserAccount.active(
                "USR-001",
                "admin@example.com",
                "Admin User",
                Set.of(UserRole.ADMIN),
                Set.of(UserPermission.MANAGE_USERS),
                Notes.of("Account principale")
        );

        assertTrue(account.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        assertEquals(
                "USR-001 - admin@example.com - ACTIVE - roles: 1",
                adminAccount().formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentAccountsEqual() {
        UserAccount first = adminAccount();
        UserAccount second = adminAccount();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldExposeEnumDetails() {
        assertTrue(UserAccountStatus.ACTIVE.canLogin());
        assertFalse(UserAccountStatus.LOCKED.canLogin());
        assertTrue(UserAccountStatus.DELETED.isTerminal());

        assertTrue(UserRole.ADMIN.isAdministrativeRole());
        assertTrue(UserRole.DISPATCHER.isBackOfficeRole());
        assertTrue(UserRole.DRIVER.isDriverPortalRole());
        assertTrue(UserRole.CUSTOMER.isCustomerPortalRole());

        assertTrue(UserPermission.MANAGE_USERS.isSensitive());
        assertFalse(UserPermission.VIEW_SHIPMENTS.isSensitive());
    }

    private static UserAccount adminAccount() {
        return UserAccount.active(
                "USR-001",
                "admin@example.com",
                "Admin User",
                Set.of(UserRole.ADMIN),
                Set.of(
                        UserPermission.MANAGE_USERS,
                        UserPermission.VIEW_AUDIT,
                        UserPermission.MANAGE_CONFIGURATION
                ),
                Notes.empty()
        );
    }
}
