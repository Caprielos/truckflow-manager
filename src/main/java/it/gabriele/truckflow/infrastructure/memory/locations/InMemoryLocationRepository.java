package it.gabriele.truckflow.infrastructure.memory.locations;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.locations.LocationRepository;
import it.gabriele.truckflow.domain.locations.Location;
import it.gabriele.truckflow.domain.locations.LocationCode;
import it.gabriele.truckflow.domain.locations.LocationId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory implementation of the location repository port. */
public final class InMemoryLocationRepository implements LocationRepository {

  private final Map<LocationId, Location> locationsById = new HashMap<>();
  private final Map<LocationCode, LocationId> idsByCode = new HashMap<>();

  @Override
  public Location save(Location location) {
    UseCaseValidationException.requireNonNull(location, "location");

    LocationId existingId = idsByCode.get(location.code());
    if (existingId != null && !existingId.equals(location.id())) {
      throw new DuplicateResourceException("Location", location.code().value());
    }

    Location previousLocation = locationsById.put(location.id(), location);
    if (previousLocation != null && !previousLocation.code().equals(location.code())) {
      idsByCode.remove(previousLocation.code());
    }

    idsByCode.put(location.code(), location.id());
    return location;
  }

  @Override
  public Optional<Location> findById(LocationId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(locationsById.get(id));
  }

  @Override
  public Optional<Location> findByCode(LocationCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    LocationId id = idsByCode.get(code);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public boolean existsById(LocationId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return locationsById.containsKey(id);
  }

  @Override
  public boolean existsByCode(LocationCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return idsByCode.containsKey(code);
  }
}
