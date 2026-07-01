package it.gabriele.truckflow.application.usecase.shipments;

import it.gabriele.truckflow.application.command.shipments.AddShipmentItemCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.shipments.AddShipmentItemUseCase;
import it.gabriele.truckflow.application.port.out.cargo.CargoUnitRepository;
import it.gabriele.truckflow.application.port.out.shipments.ShipmentRepository;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;
import it.gabriele.truckflow.domain.shipments.items.ShipmentItem;
import java.util.ArrayList;

/** Application service that adds cargo items to shipments. */
public final class AddShipmentItemService implements AddShipmentItemUseCase {

  private final ShipmentRepository shipmentRepository;
  private final CargoUnitRepository cargoUnitRepository;

  public AddShipmentItemService(
      ShipmentRepository shipmentRepository, CargoUnitRepository cargoUnitRepository) {
    UseCaseValidationException.requireNonNull(shipmentRepository, "shipmentRepository");
    UseCaseValidationException.requireNonNull(cargoUnitRepository, "cargoUnitRepository");
    this.shipmentRepository = shipmentRepository;
    this.cargoUnitRepository = cargoUnitRepository;
  }

  @Override
  public ShipmentResult execute(AddShipmentItemCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var shipment =
        shipmentRepository
            .findById(command.shipmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Shipment", command.shipmentId()));

    if (!cargoUnitRepository.existsById(command.cargoId())) {
      throw new ResourceNotFoundException("CargoUnit", command.cargoId());
    }

    var updatedShipment = ShipmentMutationSupport.copyOf(shipment);
    var updatedItems = new ArrayList<>(updatedShipment.items());
    updatedItems.add(
        new ShipmentItem(
            null, command.cargoId(), command.quantity(), command.unitOfMeasure(), command.notes()));

    updatedShipment.replaceItems(updatedItems);
    return ShipmentResult.from(shipmentRepository.save(updatedShipment));
  }
}
