package it.gabriele.truckflow.domain.shipments;

public record ShipmentNotes(String internalNotes, String externalNotes) {

  public ShipmentNotes {
    internalNotes = ShipmentValidation.normalize(internalNotes);
    externalNotes = ShipmentValidation.normalize(externalNotes);
  }

  public static ShipmentNotes empty() {
    return new ShipmentNotes("", "");
  }
}
