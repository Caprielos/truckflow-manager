package it.gabriele.truckflow.domain.cargo;

import java.math.BigDecimal;

public record CargoTemperature(
    BigDecimal requiredMinCelsius,
    BigDecimal requiredMaxCelsius,
    boolean controlled,
    String notes) {

  public CargoTemperature {
    notes = CargoValidation.normalize(notes);

    if (requiredMinCelsius != null
        && requiredMaxCelsius != null
        && requiredMinCelsius.compareTo(requiredMaxCelsius) > 0) {
      throw new IllegalArgumentException(
          "requiredMinCelsius cannot be greater than requiredMaxCelsius.");
    }

    if (controlled && requiredMinCelsius == null && requiredMaxCelsius == null) {
      throw new IllegalArgumentException(
          "At least one temperature limit is required when temperature is controlled.");
    }
  }

  public static CargoTemperature uncontrolled() {
    return new CargoTemperature(null, null, false, "");
  }
}
