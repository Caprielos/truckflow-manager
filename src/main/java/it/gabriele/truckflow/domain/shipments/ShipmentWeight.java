package it.gabriele.truckflow.domain.shipments;

import java.math.BigDecimal;

public record ShipmentWeight(
    BigDecimal grossWeight, BigDecimal netWeight, ShipmentWeightUnit unit) {

  public ShipmentWeight {
    grossWeight = ShipmentValidation.nonNegativeOrNull(grossWeight, "grossWeight");
    netWeight = ShipmentValidation.nonNegativeOrNull(netWeight, "netWeight");
    unit = ShipmentValidation.requireNonNull(unit, "unit");

    if (grossWeight != null && netWeight != null && netWeight.compareTo(grossWeight) > 0) {
      throw new IllegalArgumentException("netWeight cannot be greater than grossWeight.");
    }
  }

  public static ShipmentWeight kg(BigDecimal grossWeight, BigDecimal netWeight) {
    return new ShipmentWeight(grossWeight, netWeight, ShipmentWeightUnit.KG);
  }
}
