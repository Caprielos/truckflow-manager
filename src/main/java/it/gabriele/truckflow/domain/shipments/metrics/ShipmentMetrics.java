package it.gabriele.truckflow.domain.shipments.metrics;

import it.gabriele.truckflow.domain.shipments.core.ShipmentValidation;

public record ShipmentMetrics(ShipmentVolume volume, ShipmentWeight weight, String notes) {

  public ShipmentMetrics {
    notes = ShipmentValidation.normalize(notes);
  }

  public boolean hasVolume() {
    return volume != null;
  }

  public boolean hasWeight() {
    return weight != null;
  }

  public static ShipmentMetrics empty() {
    return new ShipmentMetrics(null, null, "");
  }
}
