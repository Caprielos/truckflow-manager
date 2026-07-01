package it.gabriele.truckflow.application.usecase.shipments;

import it.gabriele.truckflow.domain.shipments.core.Shipment;

/**
 * Internal helper for shipment application services.
 *
 * <p>The in-memory repository keeps mutable domain aggregates. Mutation use cases therefore work on
 * a copy of the loaded aggregate and save the updated copy only after all domain validations have
 * passed. This keeps failed application operations from leaking partial state into the repository.
 */
final class ShipmentMutationSupport {

  private ShipmentMutationSupport() {}

  static Shipment copyOf(Shipment shipment) {
    return new Shipment(
        shipment.id(),
        shipment.code(),
        shipment.name(),
        shipment.description(),
        shipment.status(),
        shipment.priority(),
        shipment.serviceLevel(),
        shipment.items(),
        shipment.legs(),
        shipment.properties(),
        shipment.temperature(),
        shipment.requirementSet(),
        shipment.metrics(),
        shipment.references(),
        shipment.notes(),
        shipment.generalNotes());
  }
}
