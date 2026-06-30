package it.gabriele.truckflow.domain.vehicles.unit;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;

public record FleetCode(String value) {

  public FleetCode {
    value = VehicleValidation.requireText(value, "value").toUpperCase();

    if (!value.matches("[A-Z0-9][A-Z0-9_-]*")) {
      throw new IllegalArgumentException(
          "Fleet code can contain only uppercase letters, numbers, dashes and underscores.");
    }
  }

  public static FleetCode of(String value) {
    return new FleetCode(value);
  }
}
