package it.gabriele.truckflow.domain.users;

import java.util.Arrays;
import java.util.Set;

public record UserPermissions(Set<UserPermission> permissions) {

  public UserPermissions {
    permissions = permissions == null ? Set.of() : Set.copyOf(permissions);
  }

  public static UserPermissions empty() {
    return new UserPermissions(Set.of());
  }

  public static UserPermissions of(UserPermission... permissions) {
    if (permissions == null) {
      return empty();
    }

    return new UserPermissions(Set.copyOf(Arrays.asList(permissions)));
  }

  public boolean contains(UserPermission permission) {
    return permissions.contains(requireNonNull(permission, "permission"));
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

  private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new IllegalArgumentException(fieldName + " is required.");
    }

    return value;
  }
}
