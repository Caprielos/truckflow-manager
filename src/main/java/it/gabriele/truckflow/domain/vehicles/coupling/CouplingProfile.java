package it.gabriele.truckflow.domain.vehicles.coupling;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import it.gabriele.truckflow.domain.vehicles.exceptions.InvalidVehicleException;
import java.math.BigDecimal;

public record CouplingProfile(
    CouplingType couplingType,
    boolean canTow,
    boolean canBeTowed,
    BigDecimal maxTowableWeightKg,
    BigDecimal maxTrainWeightKg,
    String notes) {

  public CouplingProfile {
    couplingType = VehicleValidation.requireNonNull(couplingType, "couplingType");
    maxTowableWeightKg =
        VehicleValidation.nonNegativeOrNull(maxTowableWeightKg, "maxTowableWeightKg");
    maxTrainWeightKg = VehicleValidation.nonNegativeOrNull(maxTrainWeightKg, "maxTrainWeightKg");
    notes = VehicleValidation.normalize(notes);

    if (couplingType == CouplingType.NONE && (canTow || canBeTowed)) {
      throw new InvalidVehicleException("Coupling type NONE cannot tow or be towed.");
    }
  }

  public static CouplingProfile none() {
    return new CouplingProfile(CouplingType.NONE, false, false, null, null, "");
  }
}
