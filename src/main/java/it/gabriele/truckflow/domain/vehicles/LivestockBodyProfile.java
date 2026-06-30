package it.gabriele.truckflow.domain.vehicles;

public record LivestockBodyProfile(
    boolean ventilation,
    boolean wateringSystem,
    Integer animalDecks,
    boolean animalWelfareCompatible,
    String notes)
    implements VehicleBodyProfile {

  public LivestockBodyProfile {
    animalDecks = VehicleValidation.nonNegativeOrNull(animalDecks, "animalDecks");
    notes = VehicleValidation.normalize(notes);
  }

  @Override
  public VehicleBodyType bodyType() {
    return VehicleBodyType.LIVESTOCK;
  }
}
