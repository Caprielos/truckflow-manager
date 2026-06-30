package it.gabriele.truckflow.domain.vehicles;

import java.math.BigDecimal;

public record VehicleEngineSpecification(
    BigDecimal enginePowerKw,
    BigDecimal torqueNm,
    BigDecimal displacementCc,
    String emissionClass,
    BigDecimal adBlueCapacityLiters,
    BigDecimal fuelTankCapacityLiters,
    BigDecimal averageConsumptionLitersPer100Km) {

  public VehicleEngineSpecification {
    enginePowerKw = VehicleValidation.nonNegativeOrNull(enginePowerKw, "enginePowerKw");
    torqueNm = VehicleValidation.nonNegativeOrNull(torqueNm, "torqueNm");
    displacementCc = VehicleValidation.nonNegativeOrNull(displacementCc, "displacementCc");
    emissionClass = VehicleValidation.normalize(emissionClass);
    adBlueCapacityLiters =
        VehicleValidation.nonNegativeOrNull(adBlueCapacityLiters, "adBlueCapacityLiters");
    fuelTankCapacityLiters =
        VehicleValidation.nonNegativeOrNull(fuelTankCapacityLiters, "fuelTankCapacityLiters");
    averageConsumptionLitersPer100Km =
        VehicleValidation.nonNegativeOrNull(
            averageConsumptionLitersPer100Km, "averageConsumptionLitersPer100Km");
  }

  public static VehicleEngineSpecification empty() {
    return new VehicleEngineSpecification(null, null, null, "", null, null, null);
  }
}
