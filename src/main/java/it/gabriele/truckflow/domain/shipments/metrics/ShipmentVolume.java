package it.gabriele.truckflow.domain.shipments.metrics;

import it.gabriele.truckflow.domain.shipments.core.ShipmentValidation;
import java.math.BigDecimal;

public record ShipmentVolume(BigDecimal value, ShipmentVolumeUnit unit) {

  public ShipmentVolume {
    value = ShipmentValidation.requireNonNegative(value, "value");
    unit = ShipmentValidation.requireNonNull(unit, "unit");
  }

  public static ShipmentVolume cubicMeters(BigDecimal value) {
    return new ShipmentVolume(value, ShipmentVolumeUnit.CUBIC_METER);
  }
}
