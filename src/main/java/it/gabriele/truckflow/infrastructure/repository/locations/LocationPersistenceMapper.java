package it.gabriele.truckflow.infrastructure.repository.locations;

import it.gabriele.truckflow.domain.locations.GeoCoordinates;
import it.gabriele.truckflow.domain.locations.Location;
import it.gabriele.truckflow.domain.locations.LocationAddress;
import it.gabriele.truckflow.domain.locations.LocationCode;
import it.gabriele.truckflow.domain.locations.LocationId;
import it.gabriele.truckflow.domain.locations.LocationStatus;
import it.gabriele.truckflow.domain.locations.LocationType;
import it.gabriele.truckflow.infrastructure.exception.MappingException;
import it.gabriele.truckflow.infrastructure.mapping.PersistenceMapper;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/** Maps the Location aggregate to and from the file persistence record used by Punto 7E. */
public final class LocationPersistenceMapper
    implements PersistenceMapper<Location, LocationPersistenceRecord> {

  @Override
  public LocationPersistenceRecord toPersistence(Location domainModel) {
    Objects.requireNonNull(domainModel, "domainModel must not be null");

    LocationAddress address = domainModel.address();
    GeoCoordinates coordinates = domainModel.coordinates();

    return new LocationPersistenceRecord(
        domainModel.id().value().toString(),
        domainModel.code().value(),
        domainModel.name(),
        domainModel.type().name(),
        domainModel.status().name(),
        address.street(),
        address.city(),
        address.postalCode(),
        address.province(),
        address.country(),
        address.notes(),
        coordinates == null ? "" : coordinates.latitude().toPlainString(),
        coordinates == null ? "" : coordinates.longitude().toPlainString(),
        domainModel.notes());
  }

  @Override
  public Location toDomain(LocationPersistenceRecord persistenceModel) {
    Objects.requireNonNull(persistenceModel, "persistenceModel must not be null");

    try {
      return new Location(
          new LocationId(UUID.fromString(persistenceModel.id())),
          LocationCode.of(persistenceModel.code()),
          persistenceModel.name(),
          LocationType.valueOf(persistenceModel.type()),
          LocationStatus.valueOf(persistenceModel.status()),
          new LocationAddress(
              persistenceModel.street(),
              persistenceModel.city(),
              persistenceModel.postalCode(),
              persistenceModel.province(),
              persistenceModel.country(),
              persistenceModel.addressNotes()),
          coordinatesFrom(persistenceModel),
          persistenceModel.notes());
    } catch (IllegalArgumentException exception) {
      throw new MappingException("Unable to rebuild Location from persistence record.", exception);
    }
  }

  private static GeoCoordinates coordinatesFrom(LocationPersistenceRecord persistenceModel) {
    if (persistenceModel.latitude().isBlank() && persistenceModel.longitude().isBlank()) {
      return null;
    }

    if (persistenceModel.latitude().isBlank() || persistenceModel.longitude().isBlank()) {
      throw new MappingException("Latitude and longitude must be present together.");
    }

    try {
      return GeoCoordinates.of(
          new BigDecimal(persistenceModel.latitude()),
          new BigDecimal(persistenceModel.longitude()));
    } catch (NumberFormatException exception) {
      throw new MappingException(
          "Invalid coordinate values for Location persistence record.", exception);
    }
  }
}
