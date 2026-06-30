package it.gabriele.truckflow.domain.operational.common;

public record OperationalCode(String value) {

  public OperationalCode {
    value = normalize(value).toUpperCase();

    if (!value.isBlank() && !value.matches("[A-Z0-9][A-Z0-9_-]*")) {
      throw new IllegalArgumentException(
          "Operational code can contain only uppercase letters, numbers, dashes and underscores.");
    }
  }

  public static OperationalCode empty() {
    return new OperationalCode("");
  }

  public static OperationalCode of(String value) {
    return new OperationalCode(value);
  }

  public boolean isAssigned() {
    return !value.isBlank();
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
