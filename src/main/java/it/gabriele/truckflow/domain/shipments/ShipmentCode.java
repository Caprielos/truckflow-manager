package it.gabriele.truckflow.domain.shipments;

public record ShipmentCode(String value) {

  public ShipmentCode {
    value = ShipmentValidation.requireText(value, "value").toUpperCase();

    if (!value.matches("[A-Z0-9][A-Z0-9_-]*")) {
      throw new IllegalArgumentException(
          "Shipment code can contain only uppercase letters, numbers, dashes and underscores.");
    }
  }

  public static ShipmentCode of(String value) {
    return new ShipmentCode(value);
  }
}
