package it.gabriele.truckflow.domain.cargo;

import java.math.BigDecimal;

public record CargoDimensions(
    BigDecimal lengthMeters,
    BigDecimal widthMeters,
    BigDecimal heightMeters,
    BigDecimal volumeCubicMeters) {

  public CargoDimensions {
    lengthMeters = CargoValidation.nonNegativeOrNull(lengthMeters, "lengthMeters");
    widthMeters = CargoValidation.nonNegativeOrNull(widthMeters, "widthMeters");
    heightMeters = CargoValidation.nonNegativeOrNull(heightMeters, "heightMeters");
    volumeCubicMeters = CargoValidation.nonNegativeOrNull(volumeCubicMeters, "volumeCubicMeters");
  }

  public static CargoDimensions empty() {
    return new CargoDimensions(null, null, null, null);
  }
}
