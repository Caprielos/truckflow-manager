package it.gabriele.truckflow.domain.vehicles.body;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import java.math.BigDecimal;

public record TipperBodyProfile(
    String tippingDirection,
    boolean hydraulicSystem,
    BigDecimal bodyVolumeCubicMeters,
    String notes)
    implements VehicleBodyProfile {

  public TipperBodyProfile {
    tippingDirection = VehicleValidation.normalize(tippingDirection).toUpperCase();
    bodyVolumeCubicMeters =
        VehicleValidation.nonNegativeOrNull(bodyVolumeCubicMeters, "bodyVolumeCubicMeters");
    notes = VehicleValidation.normalize(notes);
  }

  @Override
  public VehicleBodyType bodyType() {
    return VehicleBodyType.TIPPER;
  }
}
