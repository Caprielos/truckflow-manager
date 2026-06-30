package it.gabriele.truckflow.domain.users;

import java.util.Set;

public final class User {

  private final UserId id;
  private Username username;
  private UserPasswordHash passwordHash;
  private Set<UserRole> roles;
  private UserPermissions permissions;
  private UserStatus status;
  private UserProfile profile;
  private UserMetadata metadata;
  private UserPreferences preferences;
  private String notes;

  public User(
      UserId id,
      Username username,
      UserPasswordHash passwordHash,
      Set<UserRole> roles,
      UserPermissions permissions,
      UserStatus status,
      UserProfile profile,
      UserMetadata metadata,
      UserPreferences preferences,
      String notes) {
    this.id = id == null ? UserId.random() : id;
    this.username = requireNonNull(username, "username");
    this.passwordHash = requireNonNull(passwordHash, "passwordHash");
    this.roles = validateRoles(roles);
    this.permissions = permissions == null ? UserPermissions.empty() : permissions;
    this.status = requireNonNull(status, "status");
    this.profile = requireNonNull(profile, "profile");
    this.metadata = requireNonNull(metadata, "metadata");
    this.preferences = preferences == null ? UserPreferences.defaults() : preferences;
    this.notes = normalize(notes);

    ensureActiveUserHasRoles();
  }

  public UserId id() {
    return id;
  }

  public Username username() {
    return username;
  }

  public UserPasswordHash passwordHash() {
    return passwordHash;
  }

  public Set<UserRole> roles() {
    return Set.copyOf(roles);
  }

  public UserPermissions permissions() {
    return permissions;
  }

  public UserStatus status() {
    return status;
  }

  public UserProfile profile() {
    return profile;
  }

  public UserMetadata metadata() {
    return metadata;
  }

  public UserPreferences preferences() {
    return preferences;
  }

  public String notes() {
    return notes;
  }

  public boolean isActive() {
    return status == UserStatus.ACTIVE;
  }

  public boolean isSuspended() {
    return status == UserStatus.SUSPENDED;
  }

  public boolean isDisabled() {
    return status == UserStatus.DISABLED;
  }

  public boolean canLogin() {
    return isActive();
  }

  public boolean hasRole(UserRole role) {
    return roles.contains(requireNonNull(role, "role"));
  }

  public boolean hasAnyRole(Set<UserRole> requiredRoles) {
    return validateRoleQuery(requiredRoles, "requiredRoles").stream().anyMatch(roles::contains);
  }

  public boolean hasAllRoles(Set<UserRole> requiredRoles) {
    return roles.containsAll(validateRoleQuery(requiredRoles, "requiredRoles"));
  }

  public boolean hasPermission(UserPermission permission) {
    return permissions.contains(permission);
  }

  public void changeUsername(Username username, String updatedBy) {
    ensureNotDisabled("A disabled user username cannot be changed.");
    this.username = requireNonNull(username, "username");
    touch(updatedBy);
  }

  public void changePasswordHash(UserPasswordHash passwordHash, String updatedBy) {
    ensureNotDisabled("A disabled user password hash cannot be changed.");
    this.passwordHash = requireNonNull(passwordHash, "passwordHash");
    touch(updatedBy);
  }

  public void addRole(UserRole role, String updatedBy) {
    ensureNotDisabled("A disabled user roles cannot be changed.");
    requireNonNull(role, "role");

    var updatedRoles = new java.util.HashSet<>(roles);
    updatedRoles.add(role);

    roles = validateRoles(updatedRoles);
    touch(updatedBy);
  }

  public void removeRole(UserRole role, String updatedBy) {
    ensureNotDisabled("A disabled user roles cannot be changed.");
    requireNonNull(role, "role");

    if (roles.size() == 1 && roles.contains(role)) {
      throw new IllegalStateException("The last user role cannot be removed.");
    }

    var updatedRoles = new java.util.HashSet<>(roles);
    updatedRoles.remove(role);

    roles = validateRoles(updatedRoles);
    ensureActiveUserHasRoles();
    touch(updatedBy);
  }

  public void grantPermission(UserPermission permission, String updatedBy) {
    ensureNotDisabled("A disabled user permissions cannot be changed.");
    permissions = permissions.add(permission);
    touch(updatedBy);
  }

  public void revokePermission(UserPermission permission, String updatedBy) {
    ensureNotDisabled("A disabled user permissions cannot be changed.");
    permissions = permissions.remove(permission);
    touch(updatedBy);
  }

  public void activate(String updatedBy) {
    if (isDisabled()) {
      throw new IllegalStateException(
          "A disabled user cannot be activated. Use reactivateDisabled instead.");
    }

    status = UserStatus.ACTIVE;
    ensureActiveUserHasRoles();
    touch(updatedBy);
  }

  public void reactivateDisabled(String updatedBy) {
    status = UserStatus.ACTIVE;
    ensureActiveUserHasRoles();
    touch(updatedBy);
  }

  public void suspend(String updatedBy) {
    ensureNotDisabled("A disabled user cannot be suspended.");
    status = UserStatus.SUSPENDED;
    touch(updatedBy);
  }

  public void disable(String updatedBy) {
    status = UserStatus.DISABLED;
    touch(updatedBy);
  }

  public void updateProfile(UserProfile profile, String updatedBy) {
    ensureNotDisabled("A disabled user profile cannot be changed.");
    this.profile = requireNonNull(profile, "profile");
    touch(updatedBy);
  }

  public void updateContact(UserContact contact, String updatedBy) {
    ensureNotDisabled("A disabled user contact cannot be changed.");
    profile = profile.withContact(contact);
    touch(updatedBy);
  }

  public void updateAddress(UserAddress address, String updatedBy) {
    ensureNotDisabled("A disabled user address cannot be changed.");
    profile = profile.withAddress(address);
    touch(updatedBy);
  }

  public void updatePreferences(UserPreferences preferences, String updatedBy) {
    ensureNotDisabled("A disabled user preferences cannot be changed.");
    this.preferences = requireNonNull(preferences, "preferences");
    touch(updatedBy);
  }

  public void updateNotes(String notes, String updatedBy) {
    ensureNotDisabled("A disabled user notes cannot be changed.");
    this.notes = normalize(notes);
    touch(updatedBy);
  }

  private void touch(String updatedBy) {
    metadata = metadata.updatedNow(requireText(updatedBy, "updatedBy"));
  }

  private void ensureNotDisabled(String message) {
    if (isDisabled()) {
      throw new IllegalStateException(message);
    }
  }

  private void ensureActiveUserHasRoles() {
    if (status == UserStatus.ACTIVE && roles.isEmpty()) {
      throw new IllegalStateException("An active user must have at least one role.");
    }
  }

  private static Set<UserRole> validateRoles(Set<UserRole> roles) {
    if (roles == null || roles.isEmpty()) {
      throw new IllegalArgumentException("At least one role is required.");
    }

    if (roles.stream().anyMatch(role -> role == null)) {
      throw new IllegalArgumentException("Roles cannot contain null values.");
    }

    return Set.copyOf(roles);
  }

  private static Set<UserRole> validateRoleQuery(Set<UserRole> roles, String fieldName) {
    requireNonNull(roles, fieldName);
    return validateRoles(roles);
  }

  private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new IllegalArgumentException(fieldName + " is required.");
    }

    return value;
  }

  private static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new IllegalArgumentException(fieldName + " is required.");
    }

    return normalized;
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
