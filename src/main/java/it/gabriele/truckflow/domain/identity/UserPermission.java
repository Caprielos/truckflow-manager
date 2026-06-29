package it.gabriele.truckflow.domain.identity;

/**
 * Permesso applicativo assegnabile a un account.
 */
public enum UserPermission {

    VIEW_SHIPMENTS(false),
    MANAGE_SHIPMENTS(true),
    VIEW_OPERATIONS(false),
    MANAGE_OPERATIONS(true),
    MANAGE_FLEET(true),
    MANAGE_DRIVERS(true),
    MANAGE_BILLING(true),
    VIEW_ECONOMICS(false),
    MANAGE_ECONOMICS(true),
    VIEW_PAYROLL(true),
    MANAGE_PAYROLL(true),
    MANAGE_FACILITIES(true),
    MANAGE_PARKING(true),
    MANAGE_INVENTORY(true),
    MANAGE_CONTRACTS(true),
    IMPORT_EXTERNAL_DATA(true),
    MANAGE_DOCUMENTS(true),
    MANAGE_CLAIMS(true),
    VIEW_REPORTS(false),
    VIEW_AUDIT(true),
    MANAGE_USERS(true),
    MANAGE_CONFIGURATION(true);

    private final boolean sensitive;

    UserPermission(boolean sensitive) {
        this.sensitive = sensitive;
    }

    public boolean isSensitive() {
        return sensitive;
    }
}
