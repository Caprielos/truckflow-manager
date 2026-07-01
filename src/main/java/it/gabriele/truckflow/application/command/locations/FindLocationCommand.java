package it.gabriele.truckflow.application.command.locations;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.domain.locations.LocationId;

/** Command used to find a location by identifier. */
public record FindLocationCommand(LocationId locationId) implements ApplicationCommand {

  public FindLocationCommand {
    UseCaseValidationException.requireNonNull(locationId, "locationId");
  }
}
