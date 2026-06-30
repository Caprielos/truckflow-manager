package it.gabriele.truckflow.domain.vehicles.specification;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;

public record VehicleCabSpecification(
    String cabType, Integer seats, boolean sleeper, boolean infotainment, boolean climateControl) {

  public VehicleCabSpecification {
    cabType = VehicleValidation.normalize(cabType);
    seats = VehicleValidation.nonNegativeOrNull(seats, "seats");
  }

  public static VehicleCabSpecification empty() {
    return new VehicleCabSpecification("", null, false, false, false);
  }
}
