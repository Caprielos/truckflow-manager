package it.gabriele.truckflow.domain.vehicles.specification;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;

public record VehicleTransmissionSpecification(
    String transmissionType, Integer gears, boolean retarder, boolean pto) {

  public VehicleTransmissionSpecification {
    transmissionType = VehicleValidation.normalize(transmissionType);
    gears = VehicleValidation.nonNegativeOrNull(gears, "gears");
  }

  public static VehicleTransmissionSpecification empty() {
    return new VehicleTransmissionSpecification("", null, false, false);
  }
}
