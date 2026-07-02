package it.gabriele.truckflow.infrastructure.repository.locations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.locations.LocationRepository;
import it.gabriele.truckflow.domain.locations.GeoCoordinates;
import it.gabriele.truckflow.domain.locations.Location;
import it.gabriele.truckflow.domain.locations.LocationAddress;
import it.gabriele.truckflow.domain.locations.LocationCode;
import it.gabriele.truckflow.domain.locations.LocationId;
import it.gabriele.truckflow.domain.locations.LocationStatus;
import it.gabriele.truckflow.domain.locations.LocationType;
import it.gabriele.truckflow.infrastructure.exception.RepositoryException;
import it.gabriele.truckflow.infrastructure.mapping.PersistenceMapper;
import it.gabriele.truckflow.infrastructure.repository.InfrastructureRepositoryAdapter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileLocationRepositoryPrototypeTest {

  @TempDir Path tempDirectory;

  @Test
  void fileLocationRepositoryImplementsApplicationPortAndInfrastructureAdapter() {
    FileLocationRepository repository =
        new FileLocationRepository(tempDirectory.resolve("locations.db"));

    assertInstanceOf(LocationRepository.class, repository);
    assertInstanceOf(InfrastructureRepositoryAdapter.class, repository);
    assertEquals("file-location-repository", repository.adapterName());
    assertEquals(LocationRepository.class.getName(), repository.implementedPortName());
  }

  @Test
  void saveAndFindLocationThroughFileBackedPrototype() {
    Path file = tempDirectory.resolve("locations.db");
    FileLocationRepository repository = new FileLocationRepository(file);
    Location location = sampleLocation("LOC_A");

    repository.save(location);

    assertTrue(Files.exists(file));
    assertTrue(repository.existsById(location.id()));
    assertTrue(repository.existsByCode(location.code()));
    assertEquals(location.code(), repository.findById(location.id()).orElseThrow().code());
    assertEquals(location.id(), repository.findByCode(location.code()).orElseThrow().id());
  }

  @Test
  void savedLocationsCanBeReadByANewRepositoryInstance() {
    Path file = tempDirectory.resolve("locations.db");
    FileLocationRepository firstRepository = new FileLocationRepository(file);
    Location location = sampleLocation("LOC_B");

    firstRepository.save(location);

    FileLocationRepository secondRepository = new FileLocationRepository(file);
    Location loaded = secondRepository.findByCode(location.code()).orElseThrow();

    assertEquals(location.id(), loaded.id());
    assertEquals(location.name(), loaded.name());
    assertEquals(location.address().city(), loaded.address().city());
    assertEquals(location.coordinates().latitude(), loaded.coordinates().latitude());
  }

  @Test
  void duplicateBusinessCodeIsRejectedByFileBackedPrototype() {
    FileLocationRepository repository =
        new FileLocationRepository(tempDirectory.resolve("locations.db"));
    repository.save(sampleLocation("DUPLICATE"));

    assertThrows(
        DuplicateResourceException.class, () -> repository.save(sampleLocation("DUPLICATE")));
  }

  @Test
  void missingFileBehavesAsAnEmptyRepository() {
    FileLocationRepository repository =
        new FileLocationRepository(tempDirectory.resolve("missing.db"));

    assertFalse(repository.existsByCode(LocationCode.of("UNKNOWN")));
    assertTrue(repository.findByCode(LocationCode.of("UNKNOWN")).isEmpty());
  }

  @Test
  void invalidInputsAreRejectedBeforeTechnicalWork() {
    FileLocationRepository repository =
        new FileLocationRepository(tempDirectory.resolve("locations.db"));

    assertThrows(UseCaseValidationException.class, () -> new FileLocationRepository(null));
    assertThrows(
        UseCaseValidationException.class,
        () -> new FileLocationRepository(tempDirectory.resolve("x"), null));
    assertThrows(UseCaseValidationException.class, () -> repository.save(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.findByCode(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsById(null));
    assertThrows(UseCaseValidationException.class, () -> repository.existsByCode(null));
  }

  @Test
  void malformedRepositoryFileIsReportedAsTechnicalRepositoryFailure() throws IOException {
    Path file = tempDirectory.resolve("locations.db");
    Files.writeString(file, "malformed-record", StandardCharsets.UTF_8);
    FileLocationRepository repository = new FileLocationRepository(file);

    assertThrows(RepositoryException.class, () -> repository.findByCode(LocationCode.of("ANY")));
  }

  @Test
  void locationMapperRoundTripsDomainAndPersistenceModels() {
    LocationPersistenceMapper mapper = new LocationPersistenceMapper();
    Location location = sampleLocation("LOC_MAP");

    LocationPersistenceRecord record = mapper.toPersistence(location);
    Location rebuilt = mapper.toDomain(record);

    assertInstanceOf(PersistenceMapper.class, mapper);
    assertEquals(location.id(), rebuilt.id());
    assertEquals(location.code(), rebuilt.code());
    assertEquals(location.status(), rebuilt.status());
    assertEquals(location.address().country(), rebuilt.address().country());
    assertEquals(location.coordinates().longitude(), rebuilt.coordinates().longitude());
  }

  private static Location sampleLocation(String code) {
    return new Location(
        LocationId.random(),
        LocationCode.of(code),
        "Central Depot " + code,
        LocationType.DEPOT,
        LocationStatus.ACTIVE,
        new LocationAddress("Via Roma 1", "Roma", "00100", "RM", "IT", "Main gate"),
        GeoCoordinates.of(new BigDecimal("41.9028"), new BigDecimal("12.4964")),
        "Pilot repository sample");
  }
}
