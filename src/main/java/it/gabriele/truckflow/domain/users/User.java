package it.gabriele.truckflow.domain.users;

import java.util.Set;
import java.util.UUID;

public final class User {

  private final UUID id;
  private final String username;
  private final String passwordHash;
  private Set<UserRole> roles;
  private UserPermissions permissions;
  private UserStatus status;
  private UserContact contact;
  private UserAddress address;
  private UserMetadata metadata;
  private UserPreferences preferences;
  private String notes;

  public User(
      UUID id,
      String username,
      String passwordHash,
      Set<UserRole> roles,
      UserPermissions permissions,
      UserStatus status,
      UserContact contact,
      UserAddress address,
      UserMetadata metadata,
      UserPreferences preferences,
      String notes) {
    this.id = id == null ? UUID.randomUUID() : id;
    this.username = requireText(username, "username");
    this.passwordHash = requireText(passwordHash, "passwordHash");
    this.roles = validateRoles(roles);
    this.permissions = permissions == null ? UserPermissions.empty() : permissions;
    this.status = requireNonNull(status, "status");
    this.contact = requireNonNull(contact, "contact");
    this.address = requireNonNull(address, "address");
    this.metadata = requireNonNull(metadata, "metadata");
    this.preferences = preferences == null ? UserPreferences.defaults() : preferences;
    this.notes = normalize(notes);
  }

  public UUID id() {
    return id;
  }

  public String username() {
    return username;
  }

  public String passwordHash() {
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

  public UserContact contact() {
    return contact;
  }

  public UserAddress address() {
    return address;
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

  public boolean hasRole(UserRole role) {
    return roles.contains(requireNonNull(role, "role"));
  }

  public boolean hasPermission(UserPermission permission) {
    return permissions.contains(permission);
  }

  public boolean isActive() {
    return status == UserStatus.ACTIVE;
  }

  public void addRole(UserRole role, String updatedBy) {
    requireNonNull(role, "role");

    var updatedRoles = new java.util.HashSet<>(roles);
    updatedRoles.add(role);

    roles = validateRoles(updatedRoles);
    touch(updatedBy);
  }

  public void removeRole(UserRole role, String updatedBy) {
    requireNonNull(role, "role");

    var updatedRoles = new java.util.HashSet<>(roles);
    updatedRoles.remove(role);

    roles = validateRoles(updatedRoles);
    touch(updatedBy);
  }

  public void grantPermission(UserPermission permission, String updatedBy) {
    permissions = permissions.add(permission);
    touch(updatedBy);
  }

  public void revokePermission(UserPermission permission, String updatedBy) {
    permissions = permissions.remove(permission);
    touch(updatedBy);
  }

  public void activate(String updatedBy) {
    status = UserStatus.ACTIVE;
    touch(updatedBy);
  }

  public void suspend(String updatedBy) {
    status = UserStatus.SUSPENDED;
    touch(updatedBy);
  }

  public void disable(String updatedBy) {
    status = UserStatus.DISABLED;
    touch(updatedBy);
  }

  public void updateContact(UserContact contact, String updatedBy) {
    this.contact = requireNonNull(contact, "contact");
    touch(updatedBy);
  }

  public void updateAddress(UserAddress address, String updatedBy) {
    this.address = requireNonNull(address, "address");
    touch(updatedBy);
  }

  public void updatePreferences(UserPreferences preferences, String updatedBy) {
    this.preferences = requireNonNull(preferences, "preferences");
    touch(updatedBy);
  }

  public void updateNotes(String notes, String updatedBy) {
    this.notes = normalize(notes);
    touch(updatedBy);
  }

  private void touch(String updatedBy) {
    metadata = metadata.updatedNow(requireText(updatedBy, "updatedBy"));
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
