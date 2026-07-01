package it.gabriele.truckflow.application.command.locations;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.locations.GeoCoordinates;
import it.gabriele.truckflow.domain.locations.LocationAddress;
import it.gabriele.truckflow.domain.locations.LocationCode;
import it.gabriele.truckflow.domain.locations.LocationStatus;
import it.gabriele.truckflow.domain.locations.LocationType;

/** Command used to register a new logistics location. */
public record RegisterLocationCommand(
    LocationCode code,
    String name,
    LocationType type,
    LocationStatus status,
    LocationAddress address,
    GeoCoordinates coordinates,
    String notes)
    implements ApplicationCommand {

  public RegisterLocationCommand {
    UseCaseValidationException.requireNonNull(code, "code");
    UseCaseValidationException.requireNotBlank(name, "name");
    UseCaseValidationException.requireNonNull(type, "type");
    UseCaseValidationException.requireNonNull(status, "status");
  }
}
