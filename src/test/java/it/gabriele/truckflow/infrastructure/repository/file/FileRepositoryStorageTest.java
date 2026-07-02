package it.gabriele.truckflow.infrastructure.repository.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.infrastructure.exception.RepositoryException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileRepositoryStorageTest {

  @TempDir Path tempDirectory;

  @Test
  void missingFileIsReadAsEmptyStorage() {
    var storage = new FileRepositoryStorage<>(missingFile(), new SampleCodec(), "sample");

    assertTrue(storage.readAll().isEmpty());
  }

  @Test
  void recordsAreWrittenAndReadBackWithSafeFieldEncoding() {
    Path file = tempDirectory.resolve("nested").resolve("records.db");
    var storage = new FileRepositoryStorage<>(file, new SampleCodec(), "sample");

    storage.writeAll(
        List.of(
            new SampleRecord("B", "line with tab\tseparator"),
            new SampleRecord("A", "unicode è and newline\nvalue")),
        Comparator.comparing(SampleRecord::code));

    List<SampleRecord> loaded = storage.readAll();

    assertEquals("A", loaded.get(0).code());
    assertEquals("unicode è and newline\nvalue", loaded.get(0).description());
    assertEquals("B", loaded.get(1).code());
    assertEquals("line with tab\tseparator", loaded.get(1).description());
  }

  @Test
  void writeCreatesParentDirectories() {
    Path file = tempDirectory.resolve("a").resolve("b").resolve("records.db");
    var storage = new FileRepositoryStorage<>(file, new SampleCodec(), "sample");

    storage.writeAll(
        List.of(new SampleRecord("A", "value")), Comparator.comparing(SampleRecord::code));

    assertTrue(Files.exists(file));
  }

  @Test
  void malformedFieldCountIsReportedAsRepositoryException() throws Exception {
    Path file = tempDirectory.resolve("records.db");
    Files.writeString(file, "single-field-only", StandardCharsets.UTF_8);
    var storage = new FileRepositoryStorage<>(file, new SampleCodec(), "sample");

    assertThrows(RepositoryException.class, storage::readAll);
  }

  @Test
  void malformedFieldEncodingIsReportedAsRepositoryException() throws Exception {
    Path file = tempDirectory.resolve("records.db");
    Files.writeString(file, "%%%\tQQ", StandardCharsets.UTF_8);
    var storage = new FileRepositoryStorage<>(file, new SampleCodec(), "sample");

    assertThrows(RepositoryException.class, storage::readAll);
  }

  @Test
  void invalidCodecOutputIsReportedAsRepositoryException() {
    var storage = new FileRepositoryStorage<>(missingFile(), new BrokenWriteCodec(), "sample");

    assertThrows(
        RepositoryException.class,
        () ->
            storage.writeAll(
                List.of(new SampleRecord("A", "value")), Comparator.comparing(SampleRecord::code)));
  }

  @Test
  void constructorRejectsInvalidInfrastructureTestingInputs() {
    assertThrows(
        UseCaseValidationException.class,
        () -> new FileRepositoryStorage<>(null, new SampleCodec(), "sample"));
    assertThrows(
        UseCaseValidationException.class,
        () -> new FileRepositoryStorage<>(missingFile(), null, "sample"));
    assertThrows(
        UseCaseValidationException.class,
        () -> new FileRepositoryStorage<>(missingFile(), new SampleCodec(), " "));
  }

  private Path missingFile() {
    return tempDirectory.resolve("missing.db");
  }

  private record SampleRecord(String code, String description) {}

  private static final class SampleCodec implements FileRecordCodec<SampleRecord> {

    @Override
    public int fieldCount() {
      return 2;
    }

    @Override
    public List<String> encode(SampleRecord record) {
      return List.of(record.code(), record.description());
    }

    @Override
    public SampleRecord decode(List<String> fields) {
      return new SampleRecord(fields.get(0), fields.get(1));
    }
  }

  private static final class BrokenWriteCodec implements FileRecordCodec<SampleRecord> {

    @Override
    public int fieldCount() {
      return 3;
    }

    @Override
    public List<String> encode(SampleRecord record) {
      return List.of(record.code(), record.description());
    }

    @Override
    public SampleRecord decode(List<String> fields) {
      return new SampleRecord(fields.get(0), fields.get(1));
    }
  }
}
