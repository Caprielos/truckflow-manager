package it.gabriele.truckflow.domain.documents;

import it.gabriele.truckflow.domain.documents.exceptions.InvalidDocumentException;
import java.util.Collection;

final class DocumentValidation {

  private DocumentValidation() {}

  static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new InvalidDocumentException(fieldName + " is required.");
    }

    return value;
  }

  static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new InvalidDocumentException(fieldName + " is required.");
    }

    return normalized;
  }

  static String normalize(String value) {
    return value == null ? "" : value.trim();
  }

  static <T> void requireNoNullElements(Collection<T> values, String fieldName) {
    if (values.stream().anyMatch(value -> value == null)) {
      throw new InvalidDocumentException(fieldName + " cannot contain null values.");
    }
  }
}
