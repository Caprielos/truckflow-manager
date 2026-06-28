package it.gabriele.truckflow.domain.identity;

/**
 * Regole di dominio per account, ruoli e permessi.
 */
public final class IdentityRules {

    private IdentityRules() {
    }

    public static boolean canLogin(UserAccount account) {
        validateAccount(account);

        return account.canLogin();
    }

    public static boolean canBeActivated(UserAccount account) {
        validateAccount(account);

        return account.getStatus() == UserAccountStatus.INVITED
                || account.getStatus() == UserAccountStatus.LOCKED
                || account.getStatus() == UserAccountStatus.DISABLED;
    }

    public static boolean canBeLocked(UserAccount account) {
        validateAccount(account);

        return account.getStatus() == UserAccountStatus.ACTIVE;
    }

    public static boolean canBeDisabled(UserAccount account) {
        validateAccount(account);

        return account.getStatus() == UserAccountStatus.INVITED
                || account.getStatus() == UserAccountStatus.ACTIVE
                || account.getStatus() == UserAccountStatus.LOCKED;
    }

    public static boolean canBeDeleted(UserAccount account) {
        validateAccount(account);

        return account.getStatus() != UserAccountStatus.DELETED;
    }

    public static boolean canManageUsers(UserAccount account) {
        validateAccount(account);

        return account.canLogin()
                && (account.isAdmin() || account.hasPermission(UserPermission.MANAGE_USERS));
    }

    public static boolean canViewAudit(UserAccount account) {
        validateAccount(account);

        return account.canLogin()
                && (account.isAdmin() || account.hasPermission(UserPermission.VIEW_AUDIT));
    }

    public static boolean canManageConfiguration(UserAccount account) {
        validateAccount(account);

        return account.canLogin()
                && (account.isAdmin() || account.hasPermission(UserPermission.MANAGE_CONFIGURATION));
    }

    public static boolean canAccessBackOffice(UserAccount account) {
        validateAccount(account);

        return account.canLogin() && account.isBackOfficeUser();
    }

    public static boolean canAccessDriverPortal(UserAccount account) {
        validateAccount(account);

        return account.canLogin() && account.isDriverPortalUser();
    }

    public static boolean canAccessCustomerPortal(UserAccount account) {
        validateAccount(account);

        return account.canLogin() && account.isCustomerPortalUser();
    }

    public static boolean canPerformSensitiveAction(
            UserAccount account,
            UserPermission permission
    ) {
        validateAccount(account);

        if (permission == null) {
            throw new IllegalArgumentException("Il permesso sensibile da verificare è obbligatorio.");
        }

        return account.canLogin()
                && permission.isSensitive()
                && (account.isAdmin() || account.hasPermission(permission));
    }

    public static boolean requiresStrongAuthentication(UserAccount account) {
        validateAccount(account);

        return account.isAdmin() || account.hasSensitivePermissions();
    }

    private static void validateAccount(UserAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("L'account è obbligatorio.");
        }
    }
}
