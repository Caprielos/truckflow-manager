package it.gabriele.truckflow.infrastructure.repository.file;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.infrastructure.exception.RepositoryException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Shared file storage utility for Punto 7F repository expansion adapters.
 *
 * <p>The format is intentionally simple, deterministic and local-only. It validates repository
 * adapter behavior without introducing a database, JPA, Spring Data or SQL schema.
 */
public final class FileRepositoryStorage<R> {

  private static final String FIELD_SEPARATOR = "\t";

  private final Path storageFile;
  private final FileRecordCodec<R> codec;
  private final String recordDescription;

  public FileRepositoryStorage(
      Path storageFile, FileRecordCodec<R> codec, String recordDescription) {
    UseCaseValidationException.requireNonNull(storageFile, "storageFile");
    UseCaseValidationException.requireNonNull(codec, "codec");
    UseCaseValidationException.requireNotBlank(recordDescription, "recordDescription");
    this.storageFile = storageFile;
    this.codec = codec;
    this.recordDescription = recordDescription.strip();
  }

  /** Reads all records. Missing files are treated as empty repositories. */
  public List<R> readAll() {
    if (!Files.exists(storageFile)) {
      return List.of();
    }

    try {
      return Files.readAllLines(storageFile, StandardCharsets.UTF_8).stream()
          .filter(line -> !line.isBlank())
          .map(this::decodeLine)
          .toList();
    } catch (IOException exception) {
      throw new RepositoryException(
          "Unable to read " + recordDescription + " repository file.", exception);
    }
  }

  /** Writes all records in a deterministic order. */
  public void writeAll(Collection<R> records, Comparator<R> comparator) {
    UseCaseValidationException.requireNonNull(records, "records");
    UseCaseValidationException.requireNonNull(comparator, "comparator");

    try {
      Path parent = storageFile.toAbsolutePath().getParent();
      if (parent != null) {
        Files.createDirectories(parent);
      }

      List<String> lines =
          records.stream()
              .filter(Objects::nonNull)
              .sorted(comparator)
              .map(this::encodeLine)
              .toList();

      Files.write(
          storageFile,
          lines,
          StandardCharsets.UTF_8,
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING,
          StandardOpenOption.WRITE);
    } catch (IOException exception) {
      throw new RepositoryException(
          "Unable to write " + recordDescription + " repository file.", exception);
    }
  }

  private String encodeLine(R record) {
    List<String> fields = codec.encode(record);
    if (fields.size() != codec.fieldCount()) {
      throw new RepositoryException("Invalid " + recordDescription + " field count while writing.");
    }

    return fields.stream()
        .map(FileRepositoryStorage::encodeField)
        .reduce((a, b) -> a + FIELD_SEPARATOR + b)
        .orElse("");
  }

  private R decodeLine(String line) {
    String[] encodedFields = line.split(FIELD_SEPARATOR, -1);
    if (encodedFields.length != codec.fieldCount()) {
      throw new RepositoryException(
          "Invalid " + recordDescription + " repository record field count.");
    }

    List<String> fields =
        java.util.Arrays.stream(encodedFields).map(FileRepositoryStorage::decodeField).toList();
    return codec.decode(fields);
  }

  private static String encodeField(String value) {
    String safeValue = Objects.toString(value, "");
    return Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString(safeValue.getBytes(StandardCharsets.UTF_8));
  }

  private static String decodeField(String value) {
    try {
      return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
    } catch (IllegalArgumentException exception) {
      throw new RepositoryException("Invalid encoded repository field.", exception);
    }
  }
}
