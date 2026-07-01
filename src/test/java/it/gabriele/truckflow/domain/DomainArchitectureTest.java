package it.gabriele.truckflow.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class DomainArchitectureTest {

  private static final Path DOMAIN_SOURCE_ROOT =
      Path.of("src", "main", "java", "it", "gabriele", "truckflow", "domain");

  @Test
  void domainLayerDoesNotImportFrameworkInfrastructureOrApplicationCode() throws IOException {
    assertTrue(Files.isDirectory(DOMAIN_SOURCE_ROOT), "Domain source folder must exist.");

    for (Path source : javaSourcesUnder(DOMAIN_SOURCE_ROOT)) {
      String content = Files.readString(source);

      assertFalse(
          content.matches("(?s).*import\\s+org\\.springframework\\..*"),
          source + " must not import Spring.");
      assertFalse(
          content.matches("(?s).*import\\s+jakarta\\.persistence\\..*"),
          source + " must not import Jakarta Persistence.");
      assertFalse(
          content.matches("(?s).*import\\s+javax\\.persistence\\..*"),
          source + " must not import Javax Persistence.");
      assertFalse(
          content.matches("(?s).*import\\s+lombok\\..*"), source + " must not import Lombok.");
      assertFalse(
          content.matches("(?s).*import\\s+it\\.gabriele\\.truckflow\\.application\\..*"),
          source + " must not import application layer code.");
      assertFalse(
          content.matches("(?s).*import\\s+it\\.gabriele\\.truckflow\\.infrastructure\\..*"),
          source + " must not import infrastructure layer code.");
    }
  }

  @Test
  void domainValidationUsesCustomExceptionsInsteadOfStandardThrowSites() throws IOException {
    for (Path source : javaSourcesUnder(DOMAIN_SOURCE_ROOT)) {
      String content = Files.readString(source);

      assertFalse(
          content.contains("throw new IllegalArgumentException"),
          source + " must use a domain custom exception instead of IllegalArgumentException.");
      assertFalse(
          content.contains("throw new IllegalStateException"),
          source + " must use a domain custom exception instead of IllegalStateException.");
    }
  }

  @Test
  void boundedContextsUseLightweightReferencesInsteadOfForeignAggregateRoots() throws IOException {
    assertPackageDoesNotImport(
        "cargo",
        List.of(
            "it.gabriele.truckflow.domain.vehicles.",
            "it.gabriele.truckflow.domain.shipments.",
            "it.gabriele.truckflow.domain.documents.",
            "it.gabriele.truckflow.domain.compliance."));

    assertPackageDoesNotImport(
        "vehicles",
        List.of(
            "it.gabriele.truckflow.domain.cargo.",
            "it.gabriele.truckflow.domain.shipments.",
            "it.gabriele.truckflow.domain.documents.",
            "it.gabriele.truckflow.domain.compliance."));

    assertPackageDoesNotImport(
        "shipments",
        List.of(
            "it.gabriele.truckflow.domain.cargo.CargoUnit;",
            "it.gabriele.truckflow.domain.locations.Location;",
            "it.gabriele.truckflow.domain.vehicles.",
            "it.gabriele.truckflow.domain.documents.",
            "it.gabriele.truckflow.domain.compliance."));

    assertPackageDoesNotImport(
        "triptemplates", List.of("it.gabriele.truckflow.domain.locations.Location;"));

    assertPackageDoesNotImport("operational", List.of("it.gabriele.truckflow.domain.users.User;"));

    assertPackageDoesNotImport(
        "documents",
        List.of(
            "it.gabriele.truckflow.domain.vehicles.",
            "it.gabriele.truckflow.domain.cargo.",
            "it.gabriele.truckflow.domain.shipments.",
            "it.gabriele.truckflow.domain.locations.",
            "it.gabriele.truckflow.domain.triptemplates.",
            "it.gabriele.truckflow.domain.operational."));

    assertPackageDoesNotImport(
        "compliance",
        List.of(
            "it.gabriele.truckflow.domain.vehicles.",
            "it.gabriele.truckflow.domain.cargo.",
            "it.gabriele.truckflow.domain.shipments.",
            "it.gabriele.truckflow.domain.documents.",
            "it.gabriele.truckflow.domain.operational."));
  }

  private static void assertPackageDoesNotImport(String packageName, List<String> forbiddenImports)
      throws IOException {
    Path packageRoot = DOMAIN_SOURCE_ROOT.resolve(packageName);
    assertTrue(Files.isDirectory(packageRoot), "Package folder must exist: " + packageName);

    for (Path source : javaSourcesUnder(packageRoot)) {
      String content = Files.readString(source);
      for (String forbiddenImport : forbiddenImports) {
        assertFalse(
            content.contains("import " + forbiddenImport),
            source + " must not import " + forbiddenImport);
      }
    }
  }

  private static List<Path> javaSourcesUnder(Path root) throws IOException {
    try (Stream<Path> paths = Files.walk(root)) {
      return paths.filter(path -> path.toString().endsWith(".java")).sorted().toList();
    }
  }
}
