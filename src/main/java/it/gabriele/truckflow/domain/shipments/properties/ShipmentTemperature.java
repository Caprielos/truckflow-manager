package it.gabriele.truckflow.domain.shipments.properties;

import it.gabriele.truckflow.domain.shipments.core.ShipmentValidation;
import it.gabriele.truckflow.domain.shipments.exceptions.InvalidShipmentException;
import java.math.BigDecimal;

public record ShipmentTemperature(
    BigDecimal requiredMinCelsius,
    BigDecimal requiredMaxCelsius,
    boolean controlled,
    String notes) {

  public ShipmentTemperature {
    notes = ShipmentValidation.normalize(notes);

    if (controlled && (requiredMinCelsius == null || requiredMaxCelsius == null)) {
      throw new InvalidShipmentException(
          "Controlled shipment temperature requires min and max temperatures.");
    }

    if (requiredMinCelsius != null
        && requiredMaxCelsius != null
        && requiredMinCelsius.compareTo(requiredMaxCelsius) > 0) {
      throw new InvalidShipmentException(
          "requiredMinCelsius cannot be greater than requiredMaxCelsius.");
    }
  }

  public static ShipmentTemperature uncontrolled() {
    return new ShipmentTemperature(null, null, false, "");
  }
}
