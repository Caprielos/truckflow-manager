package it.gabriele.truckflow.application.usecase.shipments;

import it.gabriele.truckflow.application.command.shipments.FindShipmentCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.shipments.FindShipmentUseCase;
import it.gabriele.truckflow.application.port.out.shipments.ShipmentRepository;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;

/** Application service that finds shipments. */
public final class FindShipmentService implements FindShipmentUseCase {

  private final ShipmentRepository shipmentRepository;

  public FindShipmentService(ShipmentRepository shipmentRepository) {
    UseCaseValidationException.requireNonNull(shipmentRepository, "shipmentRepository");
    this.shipmentRepository = shipmentRepository;
  }

  @Override
  public ShipmentResult execute(FindShipmentCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    return shipmentRepository
        .findById(command.shipmentId())
        .map(ShipmentResult::from)
        .orElseThrow(() -> new ResourceNotFoundException("Shipment", command.shipmentId()));
  }
}
