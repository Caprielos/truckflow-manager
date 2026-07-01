package it.gabriele.truckflow.infrastructure.memory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class InMemoryArchitectureTest {

  private static final Path MAIN_JAVA = Path.of("src/main/java");
  private static final Path INFRASTRUCTURE_MEMORY_ROOT =
      MAIN_JAVA.resolve("it/gabriele/truckflow/infrastructure/memory");

  @Test
  void inMemoryInfrastructureDoesNotDependOnFrameworksOrWebAdapters() throws IOException {
    List<String> forbiddenPatterns =
        List.of(
            "import org.springframework",
            "import jakarta.persistence",
            "import javax.persistence",
            "import lombok",
            "it.gabriele.truckflow.web");

    List<Path> violations = filesContaining(INFRASTRUCTURE_MEMORY_ROOT, forbiddenPatterns);

    assertTrue(
        violations.isEmpty(),
        () -> "In-memory infrastructure must stay lightweight and framework-free: " + violations);
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
