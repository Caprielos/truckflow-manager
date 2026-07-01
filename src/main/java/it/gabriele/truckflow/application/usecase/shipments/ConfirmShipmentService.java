package it.gabriele.truckflow.application.usecase.shipments;

import it.gabriele.truckflow.application.command.shipments.ConfirmShipmentCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.shipments.ConfirmShipmentUseCase;
import it.gabriele.truckflow.application.port.out.shipments.ShipmentRepository;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;

/** Application service that confirms shipments through the domain aggregate. */
public final class ConfirmShipmentService implements ConfirmShipmentUseCase {

  private final ShipmentRepository shipmentRepository;

  public ConfirmShipmentService(ShipmentRepository shipmentRepository) {
    UseCaseValidationException.requireNonNull(shipmentRepository, "shipmentRepository");
    this.shipmentRepository = shipmentRepository;
  }

  @Override
  public ShipmentResult execute(ConfirmShipmentCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var shipment =
        shipmentRepository
            .findById(command.shipmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Shipment", command.shipmentId()));

    var updatedShipment = ShipmentMutationSupport.copyOf(shipment);
    updatedShipment.confirm();
    return ShipmentResult.from(shipmentRepository.save(updatedShipment));
  }
}
