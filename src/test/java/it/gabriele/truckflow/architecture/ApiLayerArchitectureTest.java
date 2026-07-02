package it.gabriele.truckflow.architecture;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ApiLayerArchitectureTest {

  private static final Path MAIN_JAVA = Path.of("src/main/java");
  private static final Path DOCS = Path.of("docs");
  private static final Path API_ROOT = MAIN_JAVA.resolve("it/gabriele/truckflow/api");
  private static final Path DOMAIN_ROOT = MAIN_JAVA.resolve("it/gabriele/truckflow/domain");
  private static final Path APPLICATION_ROOT =
      MAIN_JAVA.resolve("it/gabriele/truckflow/application");

  @Test
  void apiLayerBlueprintDocumentationIsPresent() {
    assertTrue(
        Files.exists(DOCS.resolve("professional/38-api-layer-blueprint.md")),
        "Punto 8A must provide the official API Layer Blueprint document");
  }

  @Test
  void apiPackageIsOptionalDuringBlueprintPhase() {
    assertTrue(
        !Files.exists(API_ROOT) || Files.isDirectory(API_ROOT),
        "Punto 8A may run before the API package is created, "
            + "but the path must be a directory if present");
  }

  @Test
  void apiLayerDoesNotDependOnInfrastructureDomainOrConcreteRepositories() throws IOException {
    List<String> forbiddenPatterns =
        List.of(
            "it.gabriele.truckflow.infrastructure",
            "it.gabriele.truckflow.domain",
            "FileLocationRepository",
            "FileCargoUnitRepository",
            "FileDocumentRepository",
            "FileComplianceRequirementRepository",
            "infrastructure.repository",
            "repository.file");

    List<Path> violations = filesContaining(API_ROOT, forbiddenPatterns);

    assertTrue(
        violations.isEmpty(),
        () ->
            "API layer must call application use cases and must not depend on domain, "
                + "infrastructure or concrete repositories. Violations: "
                + violations);
  }

  @Test
  void domainAndApplicationLayersDoNotDependOnApiLayer() throws IOException {
    List<Path> violations =
        filesContaining(DOMAIN_ROOT, APPLICATION_ROOT, List.of("it.gabriele.truckflow.api"));

    assertTrue(
        violations.isEmpty(),
        () ->
            "Domain and application layers must not depend on API layer. Violations: "
                + violations);
  }

  @Test
  void apiLayerBlueprintDoesNotIntroducePrematureControllersOrDtos() throws IOException {
    if (!Files.exists(API_ROOT)) {
      return;
    }

    List<String> prematurePatterns =
        List.of(
            "@RestController",
            "@Controller",
            "@RequestMapping",
            "@GetMapping",
            "@PostMapping",
            "RegisterLocationRequest",
            "LocationResponse",
            "LocationApiMapper");

    List<Path> violations = filesContaining(API_ROOT, prematurePatterns);

    assertTrue(
        violations.isEmpty(),
        () ->
            "Punto 8A is only a blueprint and must not introduce controllers, DTOs or API mappers. "
                + "Violations: "
                + violations);
  }

  private static List<Path> filesContaining(Path root, List<String> patterns) throws IOException {
    return filesContaining(new Path[] {root}, patterns);
  }

  private static List<Path> filesContaining(Path firstRoot, Path secondRoot, List<String> patterns)
      throws IOException {
    return filesContaining(new Path[] {firstRoot, secondRoot}, patterns);
  }

  private static List<Path> filesContaining(Path[] roots, List<String> patterns)
      throws IOException {
    try (Stream<Path> stream = java.util.Arrays.stream(roots)) {
      return stream
          .flatMap(ApiLayerArchitectureTest::walkJavaFilesUnchecked)
          .filter(path -> containsAny(path, patterns))
          .toList();
    }
  }

  private static Stream<Path> walkJavaFilesUnchecked(Path root) {
    try {
      if (!Files.exists(root)) {
        return Stream.empty();
      }
      return Files.walk(root).filter(path -> path.toString().endsWith(".java"));
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to scan Java sources: " + root, exception);
    }
  }

  private static boolean containsAny(Path path, List<String> patterns) {
    try {
      String content = Files.readString(path);
      return patterns.stream().anyMatch(content::contains);
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to read source file: " + path, exception);
    }
  }
}
