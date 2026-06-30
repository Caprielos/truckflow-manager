package it.gabriele.truckflow.domain.vehicles;

public record VehicleTireSpecification(
    String tireSize, String tireType, Integer wheelCount, boolean twinTiresGeneral) {

  public VehicleTireSpecification {
    tireSize = VehicleValidation.normalize(tireSize);
    tireType = VehicleValidation.normalize(tireType);
    wheelCount = VehicleValidation.nonNegativeOrNull(wheelCount, "wheelCount");
  }

  public static VehicleTireSpecification empty() {
    return new VehicleTireSpecification("", "", null, false);
  }
}
