package it.gabriele.truckflow.application.port.out.locations;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.locations.Location;
import it.gabriele.truckflow.domain.locations.LocationCode;
import it.gabriele.truckflow.domain.locations.LocationId;
import java.util.Optional;

/** Outbound repository port used by location use cases. */
public interface LocationRepository extends RepositoryPort {

  Location save(Location location);

  Optional<Location> findById(LocationId id);

  Optional<Location> findByCode(LocationCode code);

  boolean existsById(LocationId id);

  boolean existsByCode(LocationCode code);
}
