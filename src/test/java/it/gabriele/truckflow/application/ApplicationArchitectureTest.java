package it.gabriele.truckflow.application;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ApplicationArchitectureTest {

  private static final Path MAIN_JAVA = Path.of("src/main/java");
  private static final Path APPLICATION_ROOT =
      MAIN_JAVA.resolve("it/gabriele/truckflow/application");
  private static final Path DOMAIN_ROOT = MAIN_JAVA.resolve("it/gabriele/truckflow/domain");

  @Test
  void applicationLayerDoesNotDependOnFrameworksOrConcreteInfrastructure() throws IOException {
    List<String> forbiddenPatterns =
        List.of(
            "import org.springframework",
            "import jakarta.persistence",
            "import javax.persistence",
            "import lombok",
            "it.gabriele.truckflow.infrastructure",
            "it.gabriele.truckflow.web");

    List<Path> violations = filesContaining(APPLICATION_ROOT, forbiddenPatterns);

    assertTrue(
        violations.isEmpty(),
        () -> "Application layer must remain technology-neutral. Violations: " + violations);
  }

  @Test
  void domainLayerDoesNotDependOnApplicationLayer() throws IOException {
    List<Path> violations =
        filesContaining(DOMAIN_ROOT, List.of("it.gabriele.truckflow.application"));

    assertTrue(
        violations.isEmpty(),
        () -> "Domain layer must not import the application layer. Violations: " + violations);
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
