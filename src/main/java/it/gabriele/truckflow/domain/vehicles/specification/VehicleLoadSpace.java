package it.gabriele.truckflow.domain.vehicles.specification;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import java.math.BigDecimal;

public record VehicleLoadSpace(
    BigDecimal internalLengthMeters,
    BigDecimal internalWidthMeters,
    BigDecimal internalHeightMeters,
    BigDecimal volumeCubicMeters,
    Integer palletCapacity) {

  public VehicleLoadSpace {
    internalLengthMeters =
        VehicleValidation.nonNegativeOrNull(internalLengthMeters, "internalLengthMeters");
    internalWidthMeters =
        VehicleValidation.nonNegativeOrNull(internalWidthMeters, "internalWidthMeters");
    internalHeightMeters =
        VehicleValidation.nonNegativeOrNull(internalHeightMeters, "internalHeightMeters");
    volumeCubicMeters = VehicleValidation.nonNegativeOrNull(volumeCubicMeters, "volumeCubicMeters");
    palletCapacity = VehicleValidation.nonNegativeOrNull(palletCapacity, "palletCapacity");
  }

  public static VehicleLoadSpace empty() {
    return new VehicleLoadSpace(null, null, null, null, null);
  }
}
