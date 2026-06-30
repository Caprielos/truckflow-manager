package it.gabriele.truckflow.domain.vehicles;

public record VehicleAxle(
    int axleNumber, boolean steerable, boolean liftable, boolean twinTires, String notes) {

  public VehicleAxle {
    axleNumber = VehicleValidation.requirePositive(axleNumber, "axleNumber");
    notes = VehicleValidation.normalize(notes);
  }
}
