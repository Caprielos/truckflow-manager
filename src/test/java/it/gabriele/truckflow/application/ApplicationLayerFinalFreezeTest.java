package it.gabriele.truckflow.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ApplicationLayerFinalFreezeTest {

  private static final Path MAIN_JAVA = Path.of("src/main/java");
  private static final Path TEST_JAVA = Path.of("src/test/java");
  private static final Path APPLICATION_ROOT =
      MAIN_JAVA.resolve("it/gabriele/truckflow/application");
  private static final Path INFRASTRUCTURE_MEMORY_ROOT =
      MAIN_JAVA.resolve("it/gabriele/truckflow/infrastructure/memory");

  private static final List<String> APPLICATION_CONTEXTS =
      List.of(
          "locations", "cargo", "shipments", "documents", "vehicles", "operational", "compliance");

  @Test
  void activeApplicationContextsHaveCompletePackageFamilies() {
    for (String context : APPLICATION_CONTEXTS) {
      assertPackageContainsJavaFiles(APPLICATION_ROOT.resolve("command").resolve(context));
      assertPackageContainsJavaFiles(APPLICATION_ROOT.resolve("result").resolve(context));
      assertPackageContainsJavaFiles(APPLICATION_ROOT.resolve("port/in").resolve(context));
      assertPackageContainsJavaFiles(APPLICATION_ROOT.resolve("port/out").resolve(context));
      assertPackageContainsJavaFiles(APPLICATION_ROOT.resolve("usecase").resolve(context));
      assertPackageContainsJavaFiles(INFRASTRUCTURE_MEMORY_ROOT.resolve(context));
    }
  }

  @Test
  void everyConcreteInboundUseCaseHasAConcreteApplicationService() throws IOException {
    long inboundUseCaseCount = countFiles(APPLICATION_ROOT.resolve("port/in"), "UseCase.java") - 1;
    long applicationServiceCount = countFiles(APPLICATION_ROOT.resolve("usecase"), "Service.java");

    assertEquals(
        inboundUseCaseCount,
        applicationServiceCount,
        "Every concrete inbound use case should have one application service implementation.");
  }

  @Test
  void finalApplicationDocumentationIsPresent() {
    IntStream.rangeClosed(16, 28)
        .mapToObj(this::applicationDocumentationPath)
        .forEach(
            path ->
                assertTrue(Files.exists(path), () -> "Missing application documentation: " + path));

    assertTrue(Files.exists(Path.of("TRUCKFLOW_PROJECT_DOCUMENTATION.md")));
    assertTrue(Files.exists(Path.of("digitalDocs/index.html")));
    assertTrue(
        Files.exists(Path.of("digitalDocs/truckflow-manager-enterprise-documentation.html")));
  }

  @Test
  void noPrematureDeliveryLayersWereIntroduced() throws IOException {
    List<Path> forbiddenPaths =
        List.of(
            MAIN_JAVA.resolve("it/gabriele/truckflow/web"),
            MAIN_JAVA.resolve("it/gabriele/truckflow/security"),
            MAIN_JAVA.resolve("it/gabriele/truckflow/infrastructure/jpa"),
            MAIN_JAVA.resolve("it/gabriele/truckflow/infrastructure/persistence"),
            MAIN_JAVA.resolve("it/gabriele/truckflow/infrastructure/database"));

    for (Path forbiddenPath : forbiddenPaths) {
      assertFalse(Files.exists(forbiddenPath), () -> "Premature layer found: " + forbiddenPath);
    }

    List<String> forbiddenPatterns =
        List.of(
            "@RestController",
            "@Controller",
            "@Entity",
            "@Table",
            "JpaRepository",
            "CrudRepository",
            "SecurityFilterChain",
            "@EnableWebSecurity");

    List<Path> violations = filesContaining(MAIN_JAVA, forbiddenPatterns);

    assertTrue(
        violations.isEmpty(),
        () -> "Point 6 must remain application-only. Premature delivery code: " + violations);
  }

  @Test
  void finalFreezeTestSuiteIsPresent() {
    assertTrue(
        Files.exists(
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/application/ApplicationArchitectureTest.java")));
    assertTrue(
        Files.exists(
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/application/usecase/ApplicationUseCaseReviewTest.java")));
    assertTrue(
        Files.exists(
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/application/usecase/ApplicationComplianceUseCaseExpansionTest.java")));
    assertTrue(
        Files.exists(
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/application/usecase/ApplicationOperationalUseCaseHardeningTest.java")));
    assertTrue(
        Files.exists(
            TEST_JAVA.resolve(
                "it/gabriele/truckflow/infrastructure/memory/InMemoryComplianceRepositoryTest.java")));
  }

  private Path applicationDocumentationPath(int number) {
    return switch (number) {
      case 16 -> Path.of("docs/16-application-layer-blueprint.md");
      case 17 -> Path.of("docs/17-application-foundation.md");
      case 18 -> Path.of("docs/18-application-repository-ports.md");
      case 19 -> Path.of("docs/19-application-in-memory-repositories.md");
      case 20 -> Path.of("docs/20-application-first-use-cases.md");
      case 21 -> Path.of("docs/21-application-use-case-hardening.md");
      case 22 -> Path.of("docs/22-application-use-case-expansion.md");
      case 23 -> Path.of("docs/23-application-use-case-expansion-review.md");
      case 24 -> Path.of("docs/24-application-use-cases-expansion-vehicles.md");
      case 25 -> Path.of("docs/25-application-use-cases-expansion-operational-roles.md");
      case 26 -> Path.of("docs/26-application-operational-use-case-hardening.md");
      case 27 -> Path.of("docs/27-application-compliance-base-use-cases.md");
      case 28 -> Path.of("docs/28-application-layer-final-review-freeze.md");
      default ->
          throw new IllegalArgumentException(
              "Unsupported application documentation number: " + number);
    };
  }

  private static void assertPackageContainsJavaFiles(Path path) {
    assertTrue(Files.isDirectory(path), () -> "Missing package directory: " + path);
    try (Stream<Path> stream = Files.list(path)) {
      assertTrue(
          stream.anyMatch(file -> file.toString().endsWith(".java")),
          () -> "Package should contain Java files: " + path);
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to inspect package " + path, exception);
    }
  }

  private static long countFiles(Path root, String suffix) throws IOException {
    if (!Files.exists(root)) {
      return 0;
    }

    try (Stream<Path> stream = Files.walk(root)) {
      return stream.filter(path -> path.toString().endsWith(suffix)).count();
    }
  }

  private static List<Path> filesContaining(Path root, List<String> patterns) throws IOException {
    if (!Files.exists(root)) {
      return List.of();
    }

    try (Stream<Path> stream = Files.walk(root)) {
      return stream
          .filter(path -> path.toString().endsWith(".java"))
          .filter(path -> containsAny(path, patterns))
          .toList();
    }
  }

  private static boolean containsAny(Path path, List<String> patterns) {
    try {
      String content = Files.readString(path);
      return patterns.stream().anyMatch(content::contains);
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to read source file " + path, exception);
    }
  }
}
