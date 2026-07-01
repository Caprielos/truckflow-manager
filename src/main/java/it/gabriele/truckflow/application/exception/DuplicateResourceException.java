package it.gabriele.truckflow.application.exception;

/** Raised when a use case detects that a resource already exists. */
public final class DuplicateResourceException extends ApplicationException {

  private final String resourceName;
  private final String identifier;

  public DuplicateResourceException(String resourceName, Object identifier) {
    super(formatMessage(resourceName, identifier));
    this.resourceName = normalize(resourceName, "resource");
    this.identifier = normalizeIdentifier(identifier);
  }

  public String resourceName() {
    return resourceName;
  }

  public String identifier() {
    return identifier;
  }

  private static String formatMessage(String resourceName, Object identifier) {
    return normalize(resourceName, "resource")
        + " already exists: "
        + normalizeIdentifier(identifier);
  }

  private static String normalize(String value, String fallback) {
    return value == null || value.isBlank() ? fallback : value.trim();
  }

  private static String normalizeIdentifier(Object identifier) {
    if (identifier == null) {
      return "<null>";
    }

    String normalizedIdentifier = identifier.toString().trim();
    return normalizedIdentifier.isBlank() ? "<blank>" : normalizedIdentifier;
  }
}
