package it.gabriele.truckflow.infrastructure.repository.locations;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.locations.LocationRepository;
import it.gabriele.truckflow.domain.locations.Location;
import it.gabriele.truckflow.domain.locations.LocationCode;
import it.gabriele.truckflow.domain.locations.LocationId;
import it.gabriele.truckflow.infrastructure.exception.RepositoryException;
import it.gabriele.truckflow.infrastructure.repository.InfrastructureRepositoryAdapter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * File-backed prototype implementation of the location repository port.
 *
 * <p>This adapter validates the Punto 7E real repository pattern without introducing a database,
 * JPA, Spring Data or SQL schema. It is intentionally small and dedicated to the Locations pilot
 * context.
 */
public final class FileLocationRepository
    implements LocationRepository, InfrastructureRepositoryAdapter {

  private static final String FIELD_SEPARATOR = "\t";
  private static final int FIELD_COUNT = 14;

  private final Path storageFile;
  private final LocationPersistenceMapper mapper;

  public FileLocationRepository(Path storageFile) {
    this(storageFile, new LocationPersistenceMapper());
  }

  public FileLocationRepository(Path storageFile, LocationPersistenceMapper mapper) {
    UseCaseValidationException.requireNonNull(storageFile, "storageFile");
    UseCaseValidationException.requireNonNull(mapper, "mapper");
    this.storageFile = storageFile;
    this.mapper = mapper;
  }

  @Override
  public String adapterName() {
    return "file-location-repository";
  }

  @Override
  public String implementedPortName() {
    return LocationRepository.class.getName();
  }

  @Override
  public Location save(Location location) {
    UseCaseValidationException.requireNonNull(location, "location");

    Map<LocationId, Location> locations = loadAllById();
    Optional<Location> duplicate =
        locations.values().stream()
            .filter(existing -> existing.code().equals(location.code()))
            .filter(existing -> !existing.id().equals(location.id()))
            .findFirst();

    if (duplicate.isPresent()) {
      throw new DuplicateResourceException("Location", location.code().value());
    }

    locations.put(location.id(), location);
    writeAll(locations);
    return location;
  }

  @Override
  public Optional<Location> findById(LocationId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(loadAllById().get(id));
  }

  @Override
  public Optional<Location> findByCode(LocationCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return loadAllById().values().stream()
        .filter(location -> location.code().equals(code))
        .findFirst();
  }

  @Override
  public boolean existsById(LocationId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return loadAllById().containsKey(id);
  }

  @Override
  public boolean existsByCode(LocationCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return findByCode(code).isPresent();
  }

  private Map<LocationId, Location> loadAllById() {
    if (!Files.exists(storageFile)) {
      return new LinkedHashMap<>();
    }

    try {
      Map<LocationId, Location> locations = new LinkedHashMap<>();
      for (String line : Files.readAllLines(storageFile, StandardCharsets.UTF_8)) {
        if (line.isBlank()) {
          continue;
        }
        Location location = mapper.toDomain(decode(line));
        locations.put(location.id(), location);
      }
      return locations;
    } catch (IOException exception) {
      throw new RepositoryException("Unable to read location repository file.", exception);
    }
  }

  private void writeAll(Map<LocationId, Location> locations) {
    try {
      Path parent = storageFile.toAbsolutePath().getParent();
      if (parent != null) {
        Files.createDirectories(parent);
      }

      List<String> lines =
          locations.values().stream()
              .sorted(Comparator.comparing(location -> location.code().value()))
              .map(mapper::toPersistence)
              .map(FileLocationRepository::encode)
              .toList();

      Files.write(
          storageFile,
          lines,
          StandardCharsets.UTF_8,
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING,
          StandardOpenOption.WRITE);
    } catch (IOException exception) {
      throw new RepositoryException("Unable to write location repository file.", exception);
    }
  }

  private static String encode(LocationPersistenceRecord record) {
    return String.join(
        FIELD_SEPARATOR,
        encodeField(record.id()),
        encodeField(record.code()),
        encodeField(record.name()),
        encodeField(record.type()),
        encodeField(record.status()),
        encodeField(record.street()),
        encodeField(record.city()),
        encodeField(record.postalCode()),
        encodeField(record.province()),
        encodeField(record.country()),
        encodeField(record.addressNotes()),
        encodeField(record.latitude()),
        encodeField(record.longitude()),
        encodeField(record.notes()));
  }

  private static LocationPersistenceRecord decode(String line) {
    String[] fields = line.split(FIELD_SEPARATOR, -1);
    if (fields.length != FIELD_COUNT) {
      throw new RepositoryException("Invalid location repository record field count.");
    }

    return new LocationPersistenceRecord(
        decodeField(fields[0]),
        decodeField(fields[1]),
        decodeField(fields[2]),
        decodeField(fields[3]),
        decodeField(fields[4]),
        decodeField(fields[5]),
        decodeField(fields[6]),
        decodeField(fields[7]),
        decodeField(fields[8]),
        decodeField(fields[9]),
        decodeField(fields[10]),
        decodeField(fields[11]),
        decodeField(fields[12]),
        decodeField(fields[13]));
  }

  private static String encodeField(String value) {
    return Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString(value.getBytes(StandardCharsets.UTF_8));
  }

  private static String decodeField(String value) {
    try {
      return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
    } catch (IllegalArgumentException exception) {
      throw new RepositoryException(
          "Invalid encoded field in location repository record.", exception);
    }
  }
}
