package it.gabriele.truckflow.domain.cargo;

import java.math.BigDecimal;

public record CargoWeights(
    BigDecimal grossWeightKg, BigDecimal netWeightKg, BigDecimal tareWeightKg) {

  public CargoWeights {
    grossWeightKg = CargoValidation.nonNegativeOrNull(grossWeightKg, "grossWeightKg");
    netWeightKg = CargoValidation.nonNegativeOrNull(netWeightKg, "netWeightKg");
    tareWeightKg = CargoValidation.nonNegativeOrNull(tareWeightKg, "tareWeightKg");

    if (grossWeightKg != null && netWeightKg != null && netWeightKg.compareTo(grossWeightKg) > 0) {
      throw new IllegalArgumentException("netWeightKg cannot be greater than grossWeightKg.");
    }

    if (grossWeightKg != null
        && tareWeightKg != null
        && tareWeightKg.compareTo(grossWeightKg) > 0) {
      throw new IllegalArgumentException("tareWeightKg cannot be greater than grossWeightKg.");
    }
  }

  public static CargoWeights empty() {
    return new CargoWeights(null, null, null);
  }
}
