package it.gabriele.truckflow.documentation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class DocumentationRestructureTest {

  private static final Path DOCS = Path.of("docs");

  @Test
  void documentationRootContainsFinalStructure() {
    assertTrue(Files.exists(DOCS.resolve("README.md")));
    assertTrue(Files.isDirectory(DOCS.resolve("old_style")));
    assertTrue(Files.isDirectory(DOCS.resolve("simple")));
    assertTrue(Files.isDirectory(DOCS.resolve("professional")));
    assertTrue(Files.isDirectory(DOCS.resolve("digital")));
    assertFalse(Files.exists(Path.of("digitalDocs")));
  }

  @Test
  void oldStyleArchiveContainsHistoricalDocumentsFromOneToThirtySeven() {
    for (int number = 1; number <= 37; number++) {
      String prefix = "%02d-".formatted(number);
      assertTrue(
          containsFileStartingWith(DOCS.resolve("old_style"), prefix),
          () -> "Missing historical documentation file starting with " + prefix);
    }
  }

  @Test
  void simpleDocumentationContainsReadableGuideAndGlossary() {
    List<String> expectedFiles =
        List.of(
            "README.md",
            "01-cos-e-truckflow-manager.md",
            "02-perche-il-progetto-e-organizzato-cosi.md",
            "03-domain-layer-spiegato-semplice.md",
            "04-application-layer-spiegato-semplice.md",
            "05-infrastructure-layer-spiegato-semplice.md",
            "06-cosa-sono-i-use-case.md",
            "07-cosa-sono-i-repository.md",
            "08-cosa-sono-i-port-e-gli-adapter.md",
            "09-perche-non-abbiamo-ancora-rest-api.md",
            "10-perche-non-abbiamo-ancora-database-jpa-security.md",
            "11-cosa-abbiamo-costruito-dal-punto-1-al-punto-7.md",
            "12-cosa-succedera-nel-punto-8.md",
            "glossario-semplice.md");

    for (String expectedFile : expectedFiles) {
      assertTrue(Files.exists(DOCS.resolve("simple").resolve(expectedFile)));
    }
  }

  @Test
  void professionalDocumentationContainsOfficialDocsAndTechnicalGlossary() {
    List<String> expectedFiles =
        List.of(
            "README.md",
            "01-project-overview.md",
            "02-architecture.md",
            "03-domain-application-infrastructure.md",
            "04-testing-and-quality.md",
            "05-roadmap-history.md",
            "06-current-status-and-next-steps.md",
            "technical-glossary.md");

    for (String expectedFile : expectedFiles) {
      assertTrue(Files.exists(DOCS.resolve("professional").resolve(expectedFile)));
    }
  }

  @Test
  void digitalDocumentationLivesUnderDocsDigital() {
    assertTrue(Files.exists(DOCS.resolve("digital/index.html")));
    assertTrue(Files.exists(DOCS.resolve("digital/styles.css")));
    assertTrue(Files.exists(DOCS.resolve("digital/README.md")));
    assertTrue(
        Files.exists(DOCS.resolve("digital/truckflow-manager-enterprise-documentation.html")));
  }

  private static boolean containsFileStartingWith(Path directory, String prefix) {
    try (var stream = Files.list(directory)) {
      return stream.anyMatch(path -> path.getFileName().toString().startsWith(prefix));
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to inspect documentation directory", exception);
    }
  }
}
