package it.gabriele.truckflow.application.command.shipments;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.shipments.core.ShipmentCode;
import it.gabriele.truckflow.domain.shipments.core.ShipmentPriority;
import it.gabriele.truckflow.domain.shipments.core.ShipmentServiceLevel;
import it.gabriele.truckflow.domain.shipments.metrics.ShipmentMetrics;
import it.gabriele.truckflow.domain.shipments.notes.ShipmentNotes;
import it.gabriele.truckflow.domain.shipments.properties.ShipmentProperties;
import it.gabriele.truckflow.domain.shipments.properties.ShipmentTemperature;
import it.gabriele.truckflow.domain.shipments.references.ShipmentReferences;
import it.gabriele.truckflow.domain.shipments.requirements.ShipmentRequirementSet;

/** Command used to create a new draft shipment. */
public record CreateShipmentCommand(
    ShipmentCode code,
    String name,
    String description,
    ShipmentPriority priority,
    ShipmentServiceLevel serviceLevel,
    ShipmentProperties properties,
    ShipmentTemperature temperature,
    ShipmentRequirementSet requirementSet,
    ShipmentMetrics metrics,
    ShipmentReferences references,
    ShipmentNotes notes,
    String generalNotes)
    implements ApplicationCommand {

  public CreateShipmentCommand {
    UseCaseValidationException.requireNonNull(code, "code");
    UseCaseValidationException.requireNotBlank(name, "name");
    UseCaseValidationException.requireNonNull(priority, "priority");
    UseCaseValidationException.requireNonNull(serviceLevel, "serviceLevel");
  }
}
