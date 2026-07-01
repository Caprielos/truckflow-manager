package it.gabriele.truckflow.application.port.in.locations;

import it.gabriele.truckflow.application.command.locations.RegisterLocationCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.locations.LocationResult;

/** Inbound port for registering a location. */
public interface RegisterLocationUseCase extends UseCase<RegisterLocationCommand, LocationResult> {}
