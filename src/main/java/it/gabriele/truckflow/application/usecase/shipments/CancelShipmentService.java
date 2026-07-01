package it.gabriele.truckflow.application.usecase.shipments;

import it.gabriele.truckflow.application.command.shipments.CancelShipmentCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.shipments.CancelShipmentUseCase;
import it.gabriele.truckflow.application.port.out.shipments.ShipmentRepository;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;

/** Application service that cancels shipments through the domain aggregate. */
public final class CancelShipmentService implements CancelShipmentUseCase {

  private final ShipmentRepository shipmentRepository;

  public CancelShipmentService(ShipmentRepository shipmentRepository) {
    UseCaseValidationException.requireNonNull(shipmentRepository, "shipmentRepository");
    this.shipmentRepository = shipmentRepository;
  }

  @Override
  public ShipmentResult execute(CancelShipmentCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var shipment =
        shipmentRepository
            .findById(command.shipmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Shipment", command.shipmentId()));

    var updatedShipment = ShipmentMutationSupport.copyOf(shipment);
    updatedShipment.cancel();
    return ShipmentResult.from(shipmentRepository.save(updatedShipment));
  }
}
