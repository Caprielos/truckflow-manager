package it.gabriele.truckflow.domain.vehicles.body;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import java.math.BigDecimal;

public record TankBodyProfile(
    BigDecimal tankCapacityLiters,
    Integer compartments,
    boolean foodGrade,
    boolean chemicalGrade,
    boolean fuelGrade,
    boolean adrCompatible,
    String notes)
    implements VehicleBodyProfile {

  public TankBodyProfile {
    tankCapacityLiters =
        VehicleValidation.nonNegativeOrNull(tankCapacityLiters, "tankCapacityLiters");
    compartments = VehicleValidation.nonNegativeOrNull(compartments, "compartments");
    notes = VehicleValidation.normalize(notes);
  }

  @Override
  public VehicleBodyType bodyType() {
    return VehicleBodyType.TANK;
  }
}
