package it.gabriele.truckflow.domain.identity;

import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;
import java.util.Set;

/** Account applicativo. È separato da Driver, Customer e contatti cliente. */
public final class UserAccount {

  private static final int MAX_ACCOUNT_ID_LENGTH = 50;
  private static final int MAX_EMAIL_LENGTH = 254;
  private static final int MAX_DISPLAY_NAME_LENGTH = 150;

  private final String accountId;
  private final String email;
  private final String displayName;
  private final UserAccountStatus status;
  private final Set<UserRole> roles;
  private final Set<UserPermission> permissions;
  private final Notes notes;

  private UserAccount(
      String accountId,
      String email,
      String displayName,
      UserAccountStatus status,
      Set<UserRole> roles,
      Set<UserPermission> permissions,
      Notes notes) {
    this.accountId = validateAccountId(accountId);
    this.email = validateEmail(email);
    this.displayName = validateDisplayName(displayName);

    if (status == null) {
      throw new IllegalArgumentException("Lo stato account è obbligatorio.");
    }

    this.roles = validateRoles(roles);
    this.permissions = validatePermissions(permissions);

    if (notes == null) {
      throw new IllegalArgumentException("Le note account sono obbligatorie.");
    }

    this.status = status;
    this.notes = notes;
  }

  public static UserAccount active(
      String accountId,
      String email,
      String displayName,
      Set<UserRole> roles,
      Set<UserPermission> permissions,
      Notes notes) {
    return new UserAccount(
        accountId, email, displayName, UserAccountStatus.ACTIVE, roles, permissions, notes);
  }

  public static UserAccount invited(
      String accountId,
      String email,
      String displayName,
      Set<UserRole> roles,
      Set<UserPermission> permissions,
      Notes notes) {
    return new UserAccount(
        accountId, email, displayName, UserAccountStatus.INVITED, roles, permissions, notes);
  }

  public static UserAccount locked(
      String accountId,
      String email,
      String displayName,
      Set<UserRole> roles,
      Set<UserPermission> permissions,
      Notes notes) {
    return new UserAccount(
        accountId, email, displayName, UserAccountStatus.LOCKED, roles, permissions, notes);
  }

  public static UserAccount disabled(
      String accountId,
      String email,
      String displayName,
      Set<UserRole> roles,
      Set<UserPermission> permissions,
      Notes notes) {
    return new UserAccount(
        accountId, email, displayName, UserAccountStatus.DISABLED, roles, permissions, notes);
  }

  private static String validateAccountId(String accountId) {
    if (accountId == null) {
      throw new IllegalArgumentException("L'id account è obbligatorio.");
    }

    String normalizedAccountId = accountId.trim().toUpperCase();

    if (normalizedAccountId.isEmpty()) {
      throw new IllegalArgumentException("L'id account non può essere vuoto.");
    }

    if (normalizedAccountId.length() > MAX_ACCOUNT_ID_LENGTH) {
      throw new IllegalArgumentException(
          "L'id account non può superare " + MAX_ACCOUNT_ID_LENGTH + " caratteri.");
    }

    if (!normalizedAccountId.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "L'id account può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedAccountId;
  }

  private static String validateEmail(String email) {
    if (email == null) {
      throw new IllegalArgumentException("L'email account è obbligatoria.");
    }

    String normalizedEmail = email.trim().toLowerCase();

    if (normalizedEmail.isEmpty()) {
      throw new IllegalArgumentException("L'email account non può essere vuota.");
    }

    if (normalizedEmail.length() > MAX_EMAIL_LENGTH) {
      throw new IllegalArgumentException(
          "L'email account non può superare " + MAX_EMAIL_LENGTH + " caratteri.");
    }

    if (!normalizedEmail.matches("[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}")) {
      throw new IllegalArgumentException("L'email account non è valida.");
    }

    return normalizedEmail;
  }

  private static String validateDisplayName(String displayName) {
    if (displayName == null) {
      throw new IllegalArgumentException("Il nome visualizzato account è obbligatorio.");
    }

    String normalizedDisplayName = displayName.trim();

    if (normalizedDisplayName.isEmpty()) {
      throw new IllegalArgumentException("Il nome visualizzato account non può essere vuoto.");
    }

    if (normalizedDisplayName.length() > MAX_DISPLAY_NAME_LENGTH) {
      throw new IllegalArgumentException(
          "Il nome visualizzato account non può superare "
              + MAX_DISPLAY_NAME_LENGTH
              + " caratteri.");
    }

    return normalizedDisplayName;
  }

  private static Set<UserRole> validateRoles(Set<UserRole> roles) {
    if (roles == null) {
      throw new IllegalArgumentException("I ruoli account sono obbligatori.");
    }

    if (roles.isEmpty()) {
      throw new IllegalArgumentException("L'account deve avere almeno un ruolo.");
    }

    if (roles.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("I ruoli account non possono contenere valori nulli.");
    }

    return Set.copyOf(roles);
  }

  private static Set<UserPermission> validatePermissions(Set<UserPermission> permissions) {
    if (permissions == null) {
      throw new IllegalArgumentException("I permessi account sono obbligatori.");
    }

    if (permissions.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("I permessi account non possono contenere valori nulli.");
    }

    return Set.copyOf(permissions);
  }

  public UserAccount activate() {
    if (!IdentityRules.canBeActivated(this)) {
      throw new IllegalStateException("L'account non può essere attivato.");
    }

    return withStatus(UserAccountStatus.ACTIVE);
  }

  public UserAccount lock() {
    if (!IdentityRules.canBeLocked(this)) {
      throw new IllegalStateException("L'account non può essere bloccato.");
    }

    return withStatus(UserAccountStatus.LOCKED);
  }

  public UserAccount disable() {
    if (!IdentityRules.canBeDisabled(this)) {
      throw new IllegalStateException("L'account non può essere disabilitato.");
    }

    return withStatus(UserAccountStatus.DISABLED);
  }

  public UserAccount delete() {
    if (!IdentityRules.canBeDeleted(this)) {
      throw new IllegalStateException("L'account non può essere eliminato.");
    }

    return withStatus(UserAccountStatus.DELETED);
  }

  private UserAccount withStatus(UserAccountStatus newStatus) {
    return new UserAccount(accountId, email, displayName, newStatus, roles, permissions, notes);
  }

  public String getAccountId() {
    return accountId;
  }

  public String getEmail() {
    return email;
  }

  public String getDisplayName() {
    return displayName;
  }

  public UserAccountStatus getStatus() {
    return status;
  }

  public Set<UserRole> getRoles() {
    return roles;
  }

  public Set<UserPermission> getPermissions() {
    return permissions;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isInvited() {
    return status == UserAccountStatus.INVITED;
  }

  public boolean isActive() {
    return status == UserAccountStatus.ACTIVE;
  }

  public boolean isLocked() {
    return status == UserAccountStatus.LOCKED;
  }

  public boolean isDisabled() {
    return status == UserAccountStatus.DISABLED;
  }

  public boolean isDeleted() {
    return status == UserAccountStatus.DELETED;
  }

  public boolean canLogin() {
    return status.canLogin();
  }

  public boolean hasRole(UserRole role) {
    if (role == null) {
      throw new IllegalArgumentException("Il ruolo da verificare è obbligatorio.");
    }

    return roles.contains(role);
  }

  public boolean hasAnyRole(Set<UserRole> rolesToCheck) {
    if (rolesToCheck == null) {
      throw new IllegalArgumentException("I ruoli da verificare sono obbligatori.");
    }

    if (rolesToCheck.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException(
          "I ruoli da verificare non possono contenere valori nulli.");
    }

    return rolesToCheck.stream().anyMatch(roles::contains);
  }

  public boolean hasPermission(UserPermission permission) {
    if (permission == null) {
      throw new IllegalArgumentException("Il permesso da verificare è obbligatorio.");
    }

    return permissions.contains(permission);
  }

  public boolean hasAnyPermission(Set<UserPermission> permissionsToCheck) {
    if (permissionsToCheck == null) {
      throw new IllegalArgumentException("I permessi da verificare sono obbligatori.");
    }

    if (permissionsToCheck.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException(
          "I permessi da verificare non possono contenere valori nulli.");
    }

    return permissionsToCheck.stream().anyMatch(permissions::contains);
  }

  public boolean isAdmin() {
    return hasRole(UserRole.ADMIN);
  }

  public boolean isBackOfficeUser() {
    return roles.stream().anyMatch(UserRole::isBackOfficeRole);
  }

  public boolean isDriverPortalUser() {
    return roles.stream().anyMatch(UserRole::isDriverPortalRole);
  }

  public boolean isCustomerPortalUser() {
    return roles.stream().anyMatch(UserRole::isCustomerPortalRole);
  }

  public boolean hasSensitivePermissions() {
    return permissions.stream().anyMatch(UserPermission::isSensitive);
  }

  public boolean hasNotes() {
    return notes.hasText();
  }

  public String formatSingleLine() {
    return accountId + " - " + email + " - " + status + " - roles: " + roles.size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserAccount that)) return false;
    return accountId.equals(that.accountId)
        && email.equals(that.email)
        && displayName.equals(that.displayName)
        && status == that.status
        && roles.equals(that.roles)
        && permissions.equals(that.permissions)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, email, displayName, status, roles, permissions, notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
