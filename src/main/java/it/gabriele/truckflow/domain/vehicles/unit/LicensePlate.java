package it.gabriele.truckflow.domain.vehicles.unit;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import it.gabriele.truckflow.domain.vehicles.exceptions.InvalidVehicleException;

public record LicensePlate(String value) {

  public LicensePlate {
    value = VehicleValidation.requireText(value, "value").toUpperCase().replace(" ", "");

    if (!value.matches("[A-Z0-9][A-Z0-9-]*")) {
      throw new InvalidVehicleException(
          "License plate can contain only uppercase letters, numbers and dashes.");
    }

    if (value.length() < 3 || value.length() > 20) {
      throw new InvalidVehicleException(
          "License plate length must be between 3 and 20 characters.");
    }
  }

  public static LicensePlate of(String value) {
    return new LicensePlate(value);
  }
}
