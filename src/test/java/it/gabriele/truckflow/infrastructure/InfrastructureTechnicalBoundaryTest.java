package it.gabriele.truckflow.infrastructure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class InfrastructureTechnicalBoundaryTest {

  private static final Path MAIN_JAVA = Path.of("src/main/java");
  private static final Path DOMAIN = MAIN_JAVA.resolve("it/gabriele/truckflow/domain");
  private static final Path APPLICATION = MAIN_JAVA.resolve("it/gabriele/truckflow/application");
  private static final Path INFRASTRUCTURE =
      MAIN_JAVA.resolve("it/gabriele/truckflow/infrastructure");

  @Test
  void domainAndApplicationLayersDoNotImportInfrastructure() throws IOException {
    List<Path> leakingFiles =
        javaFiles(DOMAIN, APPLICATION).stream()
            .filter(path -> containsAny(path, "it.gabriele.truckflow.infrastructure"))
            .toList();

    assertTrue(
        leakingFiles.isEmpty(), "Core layers must not import infrastructure: " + leakingFiles);
  }

  @Test
  void infrastructureTestingKeepsRestAndSecurityOutOfMainCode() throws IOException {
    List<Path> prematureFiles =
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
                        "EnableWebSecurity"))
            .toList();

    assertTrue(
        prematureFiles.isEmpty(),
        "Punto 7G must not introduce API/security layers: " + prematureFiles);
  }

  @Test
  void infrastructureTestingKeepsJpaAndSpringDataOutOfMainCode() throws IOException {
    List<Path> prematureFiles =
        javaFiles(MAIN_JAVA).stream()
            .filter(
                path ->
                    containsAny(
                        path,
                        "jakarta.persistence",
                        "javax.persistence",
                        "@Entity",
                        "@Table",
                        "JpaRepository",
                        "CrudRepository",
                        "org.springframework.data"))
            .toList();

    assertTrue(
        prematureFiles.isEmpty(),
        "Punto 7G must not introduce JPA or Spring Data: " + prematureFiles);
  }

  @Test
  void repositoryExpansionStillUsesOnlyAllowedInfrastructurePackages() throws IOException {
    List<Path> forbiddenInfrastructurePackages =
        Files.walk(INFRASTRUCTURE)
            .filter(Files::isDirectory)
            .filter(this::isForbiddenInfrastructurePackage)
            .toList();

    assertTrue(
        forbiddenInfrastructurePackages.isEmpty(),
        "Repository testing must not create premature persistence/database packages: "
            + forbiddenInfrastructurePackages);
  }

  private static List<Path> javaFiles(Path... roots) throws IOException {
    try (var stream = java.util.Arrays.stream(roots)) {
      return stream.flatMap(InfrastructureTechnicalBoundaryTest::walkJavaFilesUnchecked).toList();
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

  private boolean isForbiddenInfrastructurePackage(Path path) {
    String normalized = path.toString().replace('\\', '/').toLowerCase(Locale.ROOT);
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
