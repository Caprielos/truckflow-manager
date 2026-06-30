package it.gabriele.truckflow.domain.vehicles.specification;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;

public record VehicleChassisSpecification(
    String suspensionType, String brakeType, boolean abs, boolean ebs, boolean esp) {

  public VehicleChassisSpecification {
    suspensionType = VehicleValidation.normalize(suspensionType);
    brakeType = VehicleValidation.normalize(brakeType);
  }

  public static VehicleChassisSpecification empty() {
    return new VehicleChassisSpecification("", "", false, false, false);
  }
}
