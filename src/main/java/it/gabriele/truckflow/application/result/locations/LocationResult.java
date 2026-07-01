package it.gabriele.truckflow.application.result.locations;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.result.ApplicationResult;
import it.gabriele.truckflow.domain.locations.Location;
import it.gabriele.truckflow.domain.locations.LocationCode;
import it.gabriele.truckflow.domain.locations.LocationId;
import it.gabriele.truckflow.domain.locations.LocationStatus;
import it.gabriele.truckflow.domain.locations.LocationType;

/** Result returned by location use cases. */
public record LocationResult(
    LocationId id, LocationCode code, String name, LocationType type, LocationStatus status)
    implements ApplicationResult {

  public static LocationResult from(Location location) {
    UseCaseValidationException.requireNonNull(location, "location");

    return new LocationResult(
        location.id(), location.code(), location.name(), location.type(), location.status());
  }
}
