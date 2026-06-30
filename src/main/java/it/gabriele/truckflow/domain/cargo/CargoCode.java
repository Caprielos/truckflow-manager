package it.gabriele.truckflow.domain.cargo;

public record CargoCode(String value) {

  public CargoCode {
    value = CargoValidation.requireText(value, "value").toUpperCase();

    if (!value.matches("[A-Z0-9][A-Z0-9_-]*")) {
      throw new IllegalArgumentException(
          "Cargo code can contain only uppercase letters, numbers, dashes and underscores.");
    }
  }

  public static CargoCode of(String value) {
    return new CargoCode(value);
  }
}
