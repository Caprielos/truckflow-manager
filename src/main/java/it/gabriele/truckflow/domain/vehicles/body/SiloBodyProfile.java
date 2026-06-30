package it.gabriele.truckflow.domain.vehicles.body;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import java.math.BigDecimal;

public record SiloBodyProfile(
    BigDecimal siloCapacityLiters, Integer compartments, boolean pneumaticDischarge, String notes)
    implements VehicleBodyProfile {

  public SiloBodyProfile {
    siloCapacityLiters =
        VehicleValidation.nonNegativeOrNull(siloCapacityLiters, "siloCapacityLiters");
    compartments = VehicleValidation.nonNegativeOrNull(compartments, "compartments");
    notes = VehicleValidation.normalize(notes);
  }

  @Override
  public VehicleBodyType bodyType() {
    return VehicleBodyType.SILO;
  }
}
