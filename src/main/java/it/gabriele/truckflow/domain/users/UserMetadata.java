package it.gabriele.truckflow.domain.users;

import java.time.Instant;

public record UserMetadata(
    Instant createdAt, Instant updatedAt, String createdBy, String updatedBy) {

  public UserMetadata {
    createdAt = requireNonNull(createdAt, "createdAt");
    updatedAt = updatedAt == null ? createdAt : updatedAt;
    createdBy = requireText(createdBy, "createdBy");
    updatedBy = normalize(updatedBy).isBlank() ? createdBy : normalize(updatedBy);

    if (updatedAt.isBefore(createdAt)) {
      throw new IllegalArgumentException("updatedAt cannot be before createdAt.");
    }
  }

  public static UserMetadata createdNow(String createdBy) {
    Instant now = Instant.now();
    return new UserMetadata(now, now, createdBy, createdBy);
  }

  public UserMetadata updatedNow(String updatedBy) {
    return new UserMetadata(createdAt, Instant.now(), createdBy, updatedBy);
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
