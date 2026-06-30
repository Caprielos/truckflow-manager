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
    Username updatedUsername = requireNonNull(username, "username");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    this.username = updatedUsername;
    touch(validatedUpdatedBy);
  }

  public void changePasswordHash(UserPasswordHash passwordHash, String updatedBy) {
    ensureNotDisabled("A disabled user password hash cannot be changed.");
    UserPasswordHash updatedPasswordHash = requireNonNull(passwordHash, "passwordHash");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    this.passwordHash = updatedPasswordHash;
    touch(validatedUpdatedBy);
  }

  public void addRole(UserRole role, String updatedBy) {
    ensureNotDisabled("A disabled user roles cannot be changed.");
    requireNonNull(role, "role");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    var updatedRoles = new java.util.HashSet<>(roles);
    updatedRoles.add(role);
    Set<UserRole> validatedRoles = validateRoles(updatedRoles);

    this.roles = validatedRoles;
    touch(validatedUpdatedBy);
  }

  public void removeRole(UserRole role, String updatedBy) {
    ensureNotDisabled("A disabled user roles cannot be changed.");
    requireNonNull(role, "role");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    if (roles.size() == 1 && roles.contains(role)) {
      throw new IllegalStateException("The last user role cannot be removed.");
    }

    var updatedRoles = new java.util.HashSet<>(roles);
    updatedRoles.remove(role);
    Set<UserRole> validatedRoles = validateRoles(updatedRoles);
    ensureActiveUserHasRoles(status, validatedRoles);

    this.roles = validatedRoles;
    touch(validatedUpdatedBy);
  }

  public void grantPermission(UserPermission permission, String updatedBy) {
    ensureNotDisabled("A disabled user permissions cannot be changed.");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");
    UserPermissions updatedPermissions = permissions.add(permission);

    this.permissions = updatedPermissions;
    touch(validatedUpdatedBy);
  }

  public void revokePermission(UserPermission permission, String updatedBy) {
    ensureNotDisabled("A disabled user permissions cannot be changed.");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");
    UserPermissions updatedPermissions = permissions.remove(permission);

    this.permissions = updatedPermissions;
    touch(validatedUpdatedBy);
  }

  public void activate(String updatedBy) {
    if (isDisabled()) {
      throw new IllegalStateException(
          "A disabled user cannot be activated. Use reactivateDisabled instead.");
    }

    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");
    ensureActiveUserHasRoles(UserStatus.ACTIVE, roles);

    this.status = UserStatus.ACTIVE;
    touch(validatedUpdatedBy);
  }

  public void reactivateDisabled(String updatedBy) {
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");
    ensureActiveUserHasRoles(UserStatus.ACTIVE, roles);

    this.status = UserStatus.ACTIVE;
    touch(validatedUpdatedBy);
  }

  public void suspend(String updatedBy) {
    ensureNotDisabled("A disabled user cannot be suspended.");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    this.status = UserStatus.SUSPENDED;
    touch(validatedUpdatedBy);
  }

  public void disable(String updatedBy) {
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    this.status = UserStatus.DISABLED;
    touch(validatedUpdatedBy);
  }

  public void updateProfile(UserProfile profile, String updatedBy) {
    ensureNotDisabled("A disabled user profile cannot be changed.");
    UserProfile updatedProfile = requireNonNull(profile, "profile");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    this.profile = updatedProfile;
    touch(validatedUpdatedBy);
  }

  public void updateContact(UserContact contact, String updatedBy) {
    ensureNotDisabled("A disabled user contact cannot be changed.");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");
    UserProfile updatedProfile = profile.withContact(contact);

    this.profile = updatedProfile;
    touch(validatedUpdatedBy);
  }

  public void updateAddress(UserAddress address, String updatedBy) {
    ensureNotDisabled("A disabled user address cannot be changed.");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");
    UserProfile updatedProfile = profile.withAddress(address);

    this.profile = updatedProfile;
    touch(validatedUpdatedBy);
  }

  public void updatePreferences(UserPreferences preferences, String updatedBy) {
    ensureNotDisabled("A disabled user preferences cannot be changed.");
    UserPreferences updatedPreferences = requireNonNull(preferences, "preferences");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    this.preferences = updatedPreferences;
    touch(validatedUpdatedBy);
  }

  public void updateNotes(String notes, String updatedBy) {
    ensureNotDisabled("A disabled user notes cannot be changed.");
    String updatedNotes = normalize(notes);
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    this.notes = updatedNotes;
    touch(validatedUpdatedBy);
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
    ensureActiveUserHasRoles(status, roles);
  }

  private static void ensureActiveUserHasRoles(UserStatus status, Set<UserRole> roles) {
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
