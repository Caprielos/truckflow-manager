package it.gabriele.truckflow.domain.shipments;

public record ShipmentReferences(
    String customerReference,
    String supplierReference,
    String internalReference,
    String purchaseOrderReference,
    String salesOrderReference,
    String notes) {

  public ShipmentReferences {
    customerReference = ShipmentValidation.normalize(customerReference);
    supplierReference = ShipmentValidation.normalize(supplierReference);
    internalReference = ShipmentValidation.normalize(internalReference);
    purchaseOrderReference = ShipmentValidation.normalize(purchaseOrderReference);
    salesOrderReference = ShipmentValidation.normalize(salesOrderReference);
    notes = ShipmentValidation.normalize(notes);
  }

  public static ShipmentReferences empty() {
    return new ShipmentReferences("", "", "", "", "", "");
  }
}
