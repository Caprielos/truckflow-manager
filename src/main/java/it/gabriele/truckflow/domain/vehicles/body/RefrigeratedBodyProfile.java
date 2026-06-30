package it.gabriele.truckflow.domain.vehicles.body;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import it.gabriele.truckflow.domain.vehicles.exceptions.InvalidVehicleException;
import java.math.BigDecimal;

public record RefrigeratedBodyProfile(
    String atpClass,
    BigDecimal minTemperatureCelsius,
    BigDecimal maxTemperatureCelsius,
    boolean dualTemperature,
    String reeferUnitBrand,
    String reeferUnitModel,
    String notes)
    implements VehicleBodyProfile {

  public RefrigeratedBodyProfile {
    atpClass = VehicleValidation.normalize(atpClass).toUpperCase();
    reeferUnitBrand = VehicleValidation.normalize(reeferUnitBrand);
    reeferUnitModel = VehicleValidation.normalize(reeferUnitModel);
    notes = VehicleValidation.normalize(notes);

    if (minTemperatureCelsius != null
        && maxTemperatureCelsius != null
        && minTemperatureCelsius.compareTo(maxTemperatureCelsius) > 0) {
      throw new InvalidVehicleException(
          "minTemperatureCelsius cannot be greater than maxTemperatureCelsius.");
    }
  }

  @Override
  public VehicleBodyType bodyType() {
    return VehicleBodyType.REFRIGERATED;
  }
}
