package it.gabriele.truckflow.domain.vehicles.specification;

import it.gabriele.truckflow.domain.vehicles.common.VehicleValidation;
import java.math.BigDecimal;

public record VehicleElectricSpecification(
    BigDecimal batteryCapacityKwh,
    String chargingType,
    String chargingConnector,
    Integer chargingTimeMinutes) {

  public VehicleElectricSpecification {
    batteryCapacityKwh =
        VehicleValidation.nonNegativeOrNull(batteryCapacityKwh, "batteryCapacityKwh");
    chargingType = VehicleValidation.normalize(chargingType);
    chargingConnector = VehicleValidation.normalize(chargingConnector);
    chargingTimeMinutes =
        VehicleValidation.nonNegativeOrNull(chargingTimeMinutes, "chargingTimeMinutes");
  }

  public static VehicleElectricSpecification empty() {
    return new VehicleElectricSpecification(null, "", "", null);
  }
}
