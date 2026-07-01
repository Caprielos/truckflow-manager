package it.gabriele.truckflow.application.port.in.locations;

import it.gabriele.truckflow.application.command.locations.FindLocationCommand;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.locations.LocationResult;

/** Inbound port for finding a location. */
public interface FindLocationUseCase extends UseCase<FindLocationCommand, LocationResult> {}
