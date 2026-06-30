package it.gabriele.truckflow.domain.operational.common;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;
import java.time.Instant;

public record OperationalMetadata(
    Instant createdAt, Instant updatedAt, String createdBy, String updatedBy) {

  public OperationalMetadata {
    createdAt = requireNonNull(createdAt, "createdAt");
    updatedAt = updatedAt == null ? createdAt : updatedAt;
    createdBy = requireText(createdBy, "createdBy");
    updatedBy = normalize(updatedBy).isBlank() ? createdBy : normalize(updatedBy);

    if (updatedAt.isBefore(createdAt)) {
      throw new DomainValidationException("updatedAt cannot be before createdAt.");
    }
  }

  public static OperationalMetadata createdNow(String createdBy) {
    Instant now = Instant.now();
    return new OperationalMetadata(now, now, createdBy, createdBy);
  }

  public OperationalMetadata updatedNow(String updatedBy) {
    return new OperationalMetadata(createdAt, Instant.now(), createdBy, updatedBy);
  }

  private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new DomainValidationException(fieldName + " is required.");
    }

    return value;
  }

  private static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new DomainValidationException(fieldName + " is required.");
    }

    return normalized;
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
