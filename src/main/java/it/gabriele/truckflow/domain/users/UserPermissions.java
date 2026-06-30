package it.gabriele.truckflow.domain.users;

import it.gabriele.truckflow.domain.users.exceptions.InvalidUserException;
import java.util.Arrays;
import java.util.Set;

public record UserPermissions(Set<UserPermission> permissions) {

  public UserPermissions {
    permissions = permissions == null ? Set.of() : validatePermissions(permissions);
  }

  public static UserPermissions empty() {
    return new UserPermissions(Set.of());
  }

  public static UserPermissions of(UserPermission... permissions) {
    if (permissions == null || permissions.length == 0) {
      return empty();
    }

    return new UserPermissions(Set.copyOf(Arrays.asList(permissions)));
  }

  public boolean contains(UserPermission permission) {
    return permissions.contains(requireNonNull(permission, "permission"));
  }

  public boolean containsAny(Set<UserPermission> requiredPermissions) {
    var validatedPermissions = validatePermissionQuery(requiredPermissions, "requiredPermissions");
    return validatedPermissions.stream().anyMatch(permissions::contains);
  }

  public boolean containsAll(Set<UserPermission> requiredPermissions) {
    var validatedPermissions = validatePermissionQuery(requiredPermissions, "requiredPermissions");
    return permissions.containsAll(validatedPermissions);
  }

  public UserPermissions add(UserPermission permission) {
    requireNonNull(permission, "permission");

    var updatedPermissions = new java.util.HashSet<>(permissions);
    updatedPermissions.add(permission);

    return new UserPermissions(updatedPermissions);
  }

  public UserPermissions remove(UserPermission permission) {
    requireNonNull(permission, "permission");

    var updatedPermissions = new java.util.HashSet<>(permissions);
    updatedPermissions.remove(permission);

    return new UserPermissions(updatedPermissions);
  }

  public boolean isEmpty() {
    return permissions.isEmpty();
  }

  private static Set<UserPermission> validatePermissions(Set<UserPermission> permissions) {
    if (permissions.stream().anyMatch(permission -> permission == null)) {
      throw new InvalidUserException("Permissions cannot contain null values.");
    }

    return Set.copyOf(permissions);
  }

  private static Set<UserPermission> validatePermissionQuery(
      Set<UserPermission> permissions, String fieldName) {
    requireNonNull(permissions, fieldName);
    return validatePermissions(permissions);
  }

  private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new InvalidUserException(fieldName + " is required.");
    }

    return value;
  }
}
