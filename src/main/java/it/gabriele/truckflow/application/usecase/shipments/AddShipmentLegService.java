package it.gabriele.truckflow.application.usecase.shipments;

import it.gabriele.truckflow.application.command.shipments.AddShipmentLegCommand;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.shipments.AddShipmentLegUseCase;
import it.gabriele.truckflow.application.port.out.locations.LocationRepository;
import it.gabriele.truckflow.application.port.out.shipments.ShipmentRepository;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;
import it.gabriele.truckflow.domain.shipments.legs.ShipmentLeg;
import java.util.ArrayList;

/** Application service that adds logistics legs to shipments. */
public final class AddShipmentLegService implements AddShipmentLegUseCase {

  private final ShipmentRepository shipmentRepository;
  private final LocationRepository locationRepository;

  public AddShipmentLegService(
      ShipmentRepository shipmentRepository, LocationRepository locationRepository) {
    UseCaseValidationException.requireNonNull(shipmentRepository, "shipmentRepository");
    UseCaseValidationException.requireNonNull(locationRepository, "locationRepository");
    this.shipmentRepository = shipmentRepository;
    this.locationRepository = locationRepository;
  }

  @Override
  public ShipmentResult execute(AddShipmentLegCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    var shipment =
        shipmentRepository
            .findById(command.shipmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Shipment", command.shipmentId()));

    if (!locationRepository.existsById(command.originLocationId())) {
      throw new ResourceNotFoundException("Location", command.originLocationId());
    }

    if (!locationRepository.existsById(command.destinationLocationId())) {
      throw new ResourceNotFoundException("Location", command.destinationLocationId());
    }

    var updatedLegs = new ArrayList<>(shipment.legs());
    updatedLegs.add(
        new ShipmentLeg(
            null,
            command.sequenceNumber(),
            command.type(),
            command.originLocationId(),
            command.destinationLocationId(),
            command.estimatedDistanceKm(),
            command.notes()));

    shipment.replaceLegs(updatedLegs);
    return ShipmentResult.from(shipmentRepository.save(shipment));
  }
}
