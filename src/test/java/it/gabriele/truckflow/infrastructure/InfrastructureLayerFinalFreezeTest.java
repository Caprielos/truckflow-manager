package it.gabriele.truckflow.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class InfrastructureLayerFinalFreezeTest {

  private static final Path MAIN_JAVA = Path.of("src/main/java");
  private static final Path TEST_JAVA = Path.of("src/test/java");
  private static final Path DOCS = Path.of("docs");
  private static final Path INFRASTRUCTURE =
      MAIN_JAVA.resolve("it/gabriele/truckflow/infrastructure");

  @Test
  void infrastructureRoadmapDocumentsArePresentFrom7aTo7h() throws IOException {
    List<String> expectedDocuments =
        List.of(
            "old_style/30-infrastructure-layer-blueprint.md",
            "old_style/31-infrastructure-foundation.md",
            "old_style/32-spring-wiring-foundation.md",
            "old_style/33-persistence-mapping-blueprint.md",
            "old_style/34-real-repository-prototype.md",
            "old_style/35-repository-expansion.md",
            "old_style/36-infrastructure-testing.md",
            "old_style/37-infrastructure-review-freeze.md");

    for (String document : expectedDocuments) {
      assertTrue(Files.exists(DOCS.resolve(document)), "Missing infrastructure doc: " + document);
    }
  }

  @Test
  void infrastructureFoundationPackagesRemainComplete() {
    List<Path> expectedPackages =
        List.of(
            INFRASTRUCTURE.resolve("adapter"),
            INFRASTRUCTURE.resolve("config"),
            INFRASTRUCTURE.resolve("config/spring"),
            INFRASTRUCTURE.resolve("exception"),
            INFRASTRUCTURE.resolve("mapping"),
            INFRASTRUCTURE.resolve("memory"),
            INFRASTRUCTURE.resolve("repository"),
            INFRASTRUCTURE.resolve("repository/file"),
            INFRASTRUCTURE.resolve("repository/locations"),
            INFRASTRUCTURE.resolve("repository/cargo"),
            INFRASTRUCTURE.resolve("repository/documents"),
            INFRASTRUCTURE.resolve("repository/compliance"),
            INFRASTRUCTURE.resolve("service"));

    for (Path expectedPackage : expectedPackages) {
      assertTrue(
          Files.isDirectory(expectedPackage), "Missing infrastructure package: " + expectedPackage);
    }
  }

  @Test
  void finalInfrastructureFreezeKeepsExpectedFileBackedRepositoryScope() throws IOException {
    Set<String> fileBackedRepositories =
        javaFiles(INFRASTRUCTURE.resolve("repository")).stream()
            .map(path -> path.getFileName().toString())
            .filter(fileName -> fileName.startsWith("File") && fileName.endsWith("Repository.java"))
            .collect(Collectors.toSet());

    assertEquals(
        Set.of(
            "FileLocationRepository.java",
            "FileCargoUnitRepository.java",
            "FileDocumentRepository.java",
            "FileComplianceRequirementRepository.java"),
        fileBackedRepositories,
        "Punto 7H freezes only the safe file-backed repository scope validated in 7E and 7F");
  }

  @Test
  void finalInfrastructureFreezeKeepsPrematureLayersOutOfMainCode() throws IOException {
    List<Path> forbiddenFiles =
        javaFiles(MAIN_JAVA).stream()
            .filter(
                path ->
                    containsAny(
                        path,
                        "@RestController",
                        "@Controller",
                        "@RequestMapping",
                        "@GetMapping",
                        "@PostMapping",
                        "SecurityFilterChain",
                        "EnableWebSecurity",
                        "jakarta.persistence",
                        "javax.persistence",
                        "@Entity",
                        "@Table",
                        "JpaRepository",
                        "CrudRepository",
                        "org.springframework.data"))
            .toList();

    assertTrue(
        forbiddenFiles.isEmpty(), "Punto 7H must not add premature layers: " + forbiddenFiles);
  }

  @Test
  void coreLayersStillDoNotDependOnInfrastructureOrSpring() throws IOException {
    Path domain = MAIN_JAVA.resolve("it/gabriele/truckflow/domain");
    Path application = MAIN_JAVA.resolve("it/gabriele/truckflow/application");

    List<Path> leakingFiles =
        javaFiles(domain, application).stream()
            .filter(
                path ->
                    containsAny(
                        path,
                        "it.gabriele.truckflow.infrastructure",
                        "org.springframework",
                        "jakarta.persistence",
                        "javax.persistence"))
            .toList();

    assertTrue(leakingFiles.isEmpty(), "Core layers must remain framework-free: " + leakingFiles);
  }

  @Test
  void springConfigurationRemainsNonWebAndMemoryProfileBased() throws IOException {
    String applicationYaml = Files.readString(Path.of("src/main/resources/application.yml"));

    assertTrue(applicationYaml.contains("web-application-type: none"));
    assertTrue(applicationYaml.contains("active: memory"));
    assertTrue(applicationYaml.contains("profile: memory"));
  }

  @Test
  void finalInfrastructureTestSuiteIsPresent() {
    List<Path> expectedTests =
        List.of(
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/infrastructure/InfrastructureFoundationTest.java"),
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/infrastructure/config/spring/SpringWiringFoundationTest.java"),
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/infrastructure/mapping/PersistenceMappingBlueprintTest.java"),
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/infrastructure/repository/locations/FileLocationRepositoryPrototypeTest.java"),
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/infrastructure/repository/FileRepositoryExpansionTest.java"),
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/infrastructure/repository/file/FileRepositoryStorageTest.java"),
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/infrastructure/repository/InfrastructureRepositoryUseCaseIntegrationTest.java"),
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/infrastructure/InfrastructureTechnicalBoundaryTest.java"));

    for (Path expectedTest : expectedTests) {
      assertTrue(Files.exists(expectedTest), "Missing infrastructure test: " + expectedTest);
    }
  }

  @Test
  void finalInfrastructureFreezeDoesNotCreateForbiddenInfrastructurePackages() throws IOException {
    List<Path> forbiddenPackages =
        Files.walk(INFRASTRUCTURE)
            .filter(Files::isDirectory)
            .filter(InfrastructureLayerFinalFreezeTest::isForbiddenInfrastructurePackage)
            .toList();

    assertTrue(
        forbiddenPackages.isEmpty(),
        "Forbidden infrastructure package found: " + forbiddenPackages);
  }

  @Test
  void documentationStatesThatPoint7IsClosedAndPoint8IsNext() throws IOException {
    String finalDoc =
        Files.readString(DOCS.resolve("old_style/37-infrastructure-review-freeze.md"));

    assertTrue(finalDoc.contains("Punto 7H"));
    assertTrue(finalDoc.contains("Punto 7"));
    assertTrue(finalDoc.contains("chiuso"));
    assertTrue(finalDoc.contains("Punto 8"));
  }

  private static List<Path> javaFiles(Path... roots) throws IOException {
    try (var stream = java.util.Arrays.stream(roots)) {
      return stream.flatMap(InfrastructureLayerFinalFreezeTest::walkJavaFilesUnchecked).toList();
    }
  }

  private static java.util.stream.Stream<Path> walkJavaFilesUnchecked(Path root) {
    try {
      if (!Files.exists(root)) {
        return java.util.stream.Stream.empty();
      }
      return Files.walk(root).filter(path -> path.toString().endsWith(".java"));
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to scan Java sources: " + root, exception);
    }
  }

  private static boolean containsAny(Path path, String... tokens) {
    try {
      String content = Files.readString(path);
      return java.util.Arrays.stream(tokens).anyMatch(content::contains);
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to read source file: " + path, exception);
    }
  }

  private static boolean isForbiddenInfrastructurePackage(Path path) {
    String normalized = path.toString().replace('\\', '/');
    return normalized.endsWith("/infrastructure/persistence")
        || normalized.contains("/infrastructure/persistence/")
        || normalized.endsWith("/infrastructure/database")
        || normalized.contains("/infrastructure/database/")
        || normalized.endsWith("/infrastructure/security")
        || normalized.contains("/infrastructure/security/")
        || normalized.endsWith("/infrastructure/web")
        || normalized.contains("/infrastructure/web/");
  }
}
