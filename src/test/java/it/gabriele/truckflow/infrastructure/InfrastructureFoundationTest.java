package it.gabriele.truckflow.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.infrastructure.config.InfrastructureProfile;
import it.gabriele.truckflow.infrastructure.exception.ExternalServiceException;
import it.gabriele.truckflow.infrastructure.exception.InfrastructureConfigurationException;
import it.gabriele.truckflow.infrastructure.exception.InfrastructureException;
import it.gabriele.truckflow.infrastructure.exception.MappingException;
import it.gabriele.truckflow.infrastructure.exception.RepositoryException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class InfrastructureFoundationTest {

  private static final Path MAIN_JAVA = Path.of("src/main/java");
  private static final Path INFRASTRUCTURE_ROOT =
      MAIN_JAVA.resolve("it/gabriele/truckflow/infrastructure");
  private static final Path DOMAIN_ROOT = MAIN_JAVA.resolve("it/gabriele/truckflow/domain");
  private static final Path APPLICATION_ROOT =
      MAIN_JAVA.resolve("it/gabriele/truckflow/application");

  @Test
  void infrastructureFoundationPackagesArePresent() {
    List<String> packages =
        List.of("adapter", "config", "exception", "mapping", "repository", "service", "memory");

    for (String packageName : packages) {
      Path packagePath = INFRASTRUCTURE_ROOT.resolve(packageName);
      assertTrue(
          Files.isDirectory(packagePath), () -> "Missing infrastructure package: " + packagePath);
      assertTrue(
          Files.exists(packagePath.resolve("package-info.java")),
          () -> "Missing package documentation: " + packagePath);
    }
  }

  @Test
  void infrastructureExceptionsShareOneTechnicalRoot() {
    List<InfrastructureException> exceptions =
        List.of(
            new RepositoryException("Repository failure"),
            new ExternalServiceException("External service failure"),
            new InfrastructureConfigurationException("Configuration failure"),
            new MappingException("Mapping failure"));

    for (InfrastructureException exception : exceptions) {
      assertInstanceOf(InfrastructureException.class, exception);
    }

    Throwable cause = new IllegalStateException("root cause");
    RepositoryException repositoryException = new RepositoryException("Repository failure", cause);

    assertEquals("Repository failure", repositoryException.getMessage());
    assertEquals(cause, repositoryException.getCause());
  }

  @Test
  void infrastructureProfilesAreExplicitAndTechnical() {
    assertEquals(
        List.of("MEMORY", "LOCAL", "TEST", "PRODUCTION"),
        Stream.of(InfrastructureProfile.values()).map(Enum::name).toList());
  }

  @Test
  void infrastructureFoundationDoesNotIntroducePrematureDeliveryOrPersistenceFrameworks()
      throws IOException {
    List<String> forbiddenPatterns =
        List.of(
            "@RestController",
            "@Controller",
            "@RequestMapping",
            "@Entity",
            "@Table",
            "JpaRepository",
            "CrudRepository",
            "SecurityFilterChain",
            "@EnableWebSecurity");

    List<Path> violations = filesContaining(INFRASTRUCTURE_ROOT, forbiddenPatterns);

    assertTrue(
        violations.isEmpty(),
        () ->
            "Infrastructure foundation must not introduce delivery or persistence frameworks: "
                + violations);
  }

  @Test
  void coreLayersStillDoNotDependOnInfrastructure() throws IOException {
    List<String> forbiddenPatterns = List.of("it.gabriele.truckflow.infrastructure");

    List<Path> domainViolations = filesContaining(DOMAIN_ROOT, forbiddenPatterns);
    List<Path> applicationViolations = filesContaining(APPLICATION_ROOT, forbiddenPatterns);

    assertTrue(
        domainViolations.isEmpty(),
        () -> "Domain must not depend on infrastructure: " + domainViolations);
    assertTrue(
        applicationViolations.isEmpty(),
        () -> "Application must not depend on infrastructure: " + applicationViolations);
  }

  @Test
  void infrastructureFoundationDocumentationIsPresent() {
    assertTrue(Files.exists(Path.of("docs/old_style/30-infrastructure-layer-blueprint.md")));
    assertTrue(Files.exists(Path.of("docs/old_style/31-infrastructure-foundation.md")));
    assertTrue(Files.exists(Path.of("docs/digital/index.html")));
    assertTrue(
        Files.exists(Path.of("docs/digital/truckflow-manager-enterprise-documentation.html")));
    assertFalse(
        Files.exists(INFRASTRUCTURE_ROOT.resolve("jpa")),
        "JPA package must not be introduced by the infrastructure foundation step.");
    assertFalse(
        Files.exists(INFRASTRUCTURE_ROOT.resolve("database")),
        "Database package must not be introduced by the infrastructure foundation step.");
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
