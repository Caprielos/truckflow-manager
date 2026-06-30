package it.gabriele.truckflow.domain.vehicles.specification;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import java.math.BigDecimal;

public record VehicleDimensions(
    BigDecimal lengthMeters,
    BigDecimal widthMeters,
    BigDecimal heightMeters,
    BigDecimal wheelbaseMeters) {

  public VehicleDimensions {
    lengthMeters = VehicleValidation.nonNegativeOrNull(lengthMeters, "lengthMeters");
    widthMeters = VehicleValidation.nonNegativeOrNull(widthMeters, "widthMeters");
    heightMeters = VehicleValidation.nonNegativeOrNull(heightMeters, "heightMeters");
    wheelbaseMeters = VehicleValidation.nonNegativeOrNull(wheelbaseMeters, "wheelbaseMeters");
  }

  public static VehicleDimensions empty() {
    return new VehicleDimensions(null, null, null, null);
  }
}
