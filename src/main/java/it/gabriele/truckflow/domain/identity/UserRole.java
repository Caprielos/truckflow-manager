package it.gabriele.truckflow.domain.identity;

/** Ruolo applicativo assegnato a un account. */
public enum UserRole {
  ADMIN(true, false, false, true),
  DISPATCHER(true, false, false, false),
  PLANNER(true, false, false, false),
  ACCOUNTING(true, false, false, false),
  MAINTENANCE(true, false, false, false),
  DRIVER(false, true, false, false),
  CUSTOMER(false, false, true, false),
  VIEWER(true, false, false, false);

  private final boolean backOfficeRole;
  private final boolean driverPortalRole;
  private final boolean customerPortalRole;
  private final boolean administrativeRole;

  UserRole(
      boolean backOfficeRole,
      boolean driverPortalRole,
      boolean customerPortalRole,
      boolean administrativeRole) {
    this.backOfficeRole = backOfficeRole;
    this.driverPortalRole = driverPortalRole;
    this.customerPortalRole = customerPortalRole;
    this.administrativeRole = administrativeRole;
  }

  public boolean isBackOfficeRole() {
    return backOfficeRole;
  }

  public boolean isDriverPortalRole() {
    return driverPortalRole;
  }

  public boolean isCustomerPortalRole() {
    return customerPortalRole;
  }

  public boolean isAdministrativeRole() {
    return administrativeRole;
  }
}
