package it.gabriele.truckflow.domain.vehicles.body;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;

public record CarCarrierBodyProfile(
    Integer carCapacity,
    boolean upperDeck,
    boolean hydraulicRamps,
    boolean adjustableDecks,
    String notes)
    implements VehicleBodyProfile {

  public CarCarrierBodyProfile {
    carCapacity = VehicleValidation.nonNegativeOrNull(carCapacity, "carCapacity");
    notes = VehicleValidation.normalize(notes);
  }

  @Override
  public VehicleBodyType bodyType() {
    return VehicleBodyType.CAR_CARRIER;
  }
}
