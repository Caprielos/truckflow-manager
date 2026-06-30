package it.gabriele.truckflow.domain.locations;

public record LocationCode(String value) {

  public LocationCode {
    value = LocationValidation.requireText(value, "value").toUpperCase();

    if (!value.matches("[A-Z0-9][A-Z0-9_-]*")) {
      throw new IllegalArgumentException(
          "Location code can contain only uppercase letters, numbers, dashes and underscores.");
    }
  }

  public static LocationCode of(String value) {
    return new LocationCode(value);
  }
}
