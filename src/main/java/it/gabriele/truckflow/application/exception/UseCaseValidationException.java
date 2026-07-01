package it.gabriele.truckflow.application.exception;

/** Raised when an application command or use case input is missing or invalid. */
public final class UseCaseValidationException extends ApplicationException {

  public UseCaseValidationException(String message) {
    super(message);
  }

  public static void requireNonNull(Object value, String fieldName) {
    if (value == null) {
      throw new UseCaseValidationException(formatFieldMessage(fieldName, "must not be null"));
    }
  }

  public static void requireNotBlank(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new UseCaseValidationException(formatFieldMessage(fieldName, "must not be blank"));
    }
  }

  private static String formatFieldMessage(String fieldName, String rule) {
    String normalizedFieldName =
        fieldName == null || fieldName.isBlank() ? "value" : fieldName.trim();
    return normalizedFieldName + " " + rule;
  }
}
