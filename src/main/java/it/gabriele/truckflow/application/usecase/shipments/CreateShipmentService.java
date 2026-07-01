package it.gabriele.truckflow.application.usecase.shipments;

import it.gabriele.truckflow.application.command.shipments.CreateShipmentCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.shipments.CreateShipmentUseCase;
import it.gabriele.truckflow.application.port.out.shipments.ShipmentRepository;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;
import it.gabriele.truckflow.domain.shipments.core.Shipment;
import it.gabriele.truckflow.domain.shipments.core.ShipmentStatus;
import java.util.List;

/** Application service that creates draft shipments. */
public final class CreateShipmentService implements CreateShipmentUseCase {

  private final ShipmentRepository shipmentRepository;

  public CreateShipmentService(ShipmentRepository shipmentRepository) {
    UseCaseValidationException.requireNonNull(shipmentRepository, "shipmentRepository");
    this.shipmentRepository = shipmentRepository;
  }

  @Override
  public ShipmentResult execute(CreateShipmentCommand command) {
    UseCaseValidationException.requireNonNull(command, "command");

    if (shipmentRepository.existsByCode(command.code())) {
      throw new DuplicateResourceException("Shipment", command.code().value());
    }

    var shipment =
        new Shipment(
            null,
            command.code(),
            command.name(),
            command.description(),
            ShipmentStatus.DRAFT,
            command.priority(),
            command.serviceLevel(),
            List.of(),
            List.of(),
            command.properties(),
            command.temperature(),
            command.requirementSet(),
            command.metrics(),
            command.references(),
            command.notes(),
            command.generalNotes());

    return ShipmentResult.from(shipmentRepository.save(shipment));
  }
}
