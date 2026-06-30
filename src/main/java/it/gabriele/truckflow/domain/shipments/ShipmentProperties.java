package it.gabriele.truckflow.domain.shipments;

public record ShipmentProperties(
    boolean fragile,
    boolean highValue,
    boolean perishable,
    boolean requiresSeparation,
    boolean stackable,
    String notes) {

  public ShipmentProperties {
    notes = ShipmentValidation.normalize(notes);
  }

  public static ShipmentProperties standard() {
    return new ShipmentProperties(false, false, false, false, true, "");
  }
}
