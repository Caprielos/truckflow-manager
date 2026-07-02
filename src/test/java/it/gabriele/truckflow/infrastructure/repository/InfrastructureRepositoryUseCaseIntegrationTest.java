package it.gabriele.truckflow.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.command.locations.FindLocationCommand;
import it.gabriele.truckflow.application.command.locations.RegisterLocationCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.usecase.locations.FindLocationService;
import it.gabriele.truckflow.application.usecase.locations.RegisterLocationService;
import it.gabriele.truckflow.domain.locations.GeoCoordinates;
import it.gabriele.truckflow.domain.locations.LocationAddress;
import it.gabriele.truckflow.domain.locations.LocationCode;
import it.gabriele.truckflow.domain.locations.LocationStatus;
import it.gabriele.truckflow.domain.locations.LocationType;
import it.gabriele.truckflow.infrastructure.repository.locations.FileLocationRepository;
import java.math.BigDecimal;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class InfrastructureRepositoryUseCaseIntegrationTest {

  @TempDir Path tempDirectory;

  @Test
  void applicationUseCasesCanRunOnFileBackedLocationRepository() {
    Path file = tempDirectory.resolve("locations.db");
    FileLocationRepository repository = new FileLocationRepository(file);
    RegisterLocationService registerLocation = new RegisterLocationService(repository);
    FindLocationService findLocation = new FindLocationService(repository);

    var registered = registerLocation.execute(command("LOC_UC"));
    var found = findLocation.execute(new FindLocationCommand(registered.id()));

    assertEquals(registered.id(), found.id());
    assertEquals(LocationCode.of("LOC_UC"), found.code());
    assertTrue(repository.existsByCode(LocationCode.of("LOC_UC")));
  }

  @Test
  void fileBackedRepositoryKeepsUseCaseDataAcrossRepositoryRecreation() {
    Path file = tempDirectory.resolve("locations.db");
    var firstRegisterService = new RegisterLocationService(new FileLocationRepository(file));

    var registered = firstRegisterService.execute(command("LOC_RELOAD"));

    var recreatedFindService = new FindLocationService(new FileLocationRepository(file));
    var foundAfterReload = recreatedFindService.execute(new FindLocationCommand(registered.id()));

    assertEquals(registered.id(), foundAfterReload.id());
    assertEquals(registered.code(), foundAfterReload.code());
  }

  @Test
  void applicationDuplicateProtectionStillWorksWithFileBackedRepository() {
    Path file = tempDirectory.resolve("locations.db");
    RegisterLocationService registerLocation =
        new RegisterLocationService(new FileLocationRepository(file));

    registerLocation.execute(command("LOC_DUP"));

    assertThrows(
        DuplicateResourceException.class, () -> registerLocation.execute(command("LOC_DUP")));
  }

  private static RegisterLocationCommand command(String code) {
    return new RegisterLocationCommand(
        LocationCode.of(code),
        "Infrastructure Test Depot " + code,
        LocationType.DEPOT,
        LocationStatus.ACTIVE,
        new LocationAddress("Via Test 1", "Roma", "00100", "RM", "IT", "Gate A"),
        GeoCoordinates.of(new BigDecimal("41.9028"), new BigDecimal("12.4964")),
        "Infrastructure testing sample");
  }
}
