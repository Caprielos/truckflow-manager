package it.gabriele.truckflow.domain.vehicles;

public record CurtainsiderBodyProfile(
    boolean slidingCurtains,
    boolean slidingRoof,
    boolean sideLoading,
    boolean roofHeightAdjustable,
    String notes)
    implements VehicleBodyProfile {

  public CurtainsiderBodyProfile {
    notes = VehicleValidation.normalize(notes);
  }

  @Override
  public VehicleBodyType bodyType() {
    return VehicleBodyType.CURTAINSIDER;
  }
}
