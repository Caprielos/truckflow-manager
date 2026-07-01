package it.gabriele.truckflow.domain.vehicles.unit;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import it.gabriele.truckflow.domain.vehicles.exceptions.InvalidVehicleException;

public record VehicleIdentificationNumber(String value) {

  public VehicleIdentificationNumber {
    value = VehicleValidation.requireText(value, "value").toUpperCase().replace(" ", "");

    if (!value.matches("[A-Z0-9-]*")) {
      throw new InvalidVehicleException(
          "Vehicle identification number can contain only uppercase letters, numbers and dashes.");
    }

    if (value.length() < 3 || value.length() > 32) {
      throw new InvalidVehicleException(
          "Vehicle identification number length must be between 3 and 32 characters.");
    }
  }

  public static VehicleIdentificationNumber of(String value) {
    return new VehicleIdentificationNumber(value);
  }
}
