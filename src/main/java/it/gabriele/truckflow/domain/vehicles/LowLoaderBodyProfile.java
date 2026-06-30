package it.gabriele.truckflow.domain.vehicles;

import java.math.BigDecimal;

public record LowLoaderBodyProfile(
    BigDecimal loadingHeightMeters,
    boolean extendable,
    boolean removableGooseneck,
    BigDecimal heavyDutyPayloadKg,
    String notes)
    implements VehicleBodyProfile {

  public LowLoaderBodyProfile {
    loadingHeightMeters =
        VehicleValidation.nonNegativeOrNull(loadingHeightMeters, "loadingHeightMeters");
    heavyDutyPayloadKg =
        VehicleValidation.nonNegativeOrNull(heavyDutyPayloadKg, "heavyDutyPayloadKg");
    notes = VehicleValidation.normalize(notes);
  }

  @Override
  public VehicleBodyType bodyType() {
    return VehicleBodyType.LOW_LOADER;
  }
}
