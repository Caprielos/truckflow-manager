package it.gabriele.truckflow.domain.vehicles;

import java.math.BigDecimal;

public record VehicleWeights(
    BigDecimal tareWeightKg,
    BigDecimal maxWeightKg,
    BigDecimal payloadKg,
    BigDecimal maxTrainWeightKg,
    BigDecimal maxTowableWeightKg) {

  public VehicleWeights {
    tareWeightKg = VehicleValidation.nonNegativeOrNull(tareWeightKg, "tareWeightKg");
    maxWeightKg = VehicleValidation.nonNegativeOrNull(maxWeightKg, "maxWeightKg");
    payloadKg = VehicleValidation.nonNegativeOrNull(payloadKg, "payloadKg");
    maxTrainWeightKg = VehicleValidation.nonNegativeOrNull(maxTrainWeightKg, "maxTrainWeightKg");
    maxTowableWeightKg =
        VehicleValidation.nonNegativeOrNull(maxTowableWeightKg, "maxTowableWeightKg");
  }

  public static VehicleWeights empty() {
    return new VehicleWeights(null, null, null, null, null);
  }
}
