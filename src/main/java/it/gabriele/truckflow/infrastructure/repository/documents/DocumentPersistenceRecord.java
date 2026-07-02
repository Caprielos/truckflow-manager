package it.gabriele.truckflow.infrastructure.repository.documents;

import java.util.Objects;

/** File-oriented persistence representation for logical document repository expansion. */
public record DocumentPersistenceRecord(
    String id,
    String code,
    String type,
    String category,
    String status,
    String title,
    String author,
    String description,
    String version,
    String tags,
    String body,
    String summary,
    String contentNotes,
    String references,
    String notes) {

  /** Creates a normalized persistence record. */
  public DocumentPersistenceRecord {
    id = requireText(id, "id");
    code = requireText(code, "code");
    type = requireText(type, "type");
    category = requireText(category, "category");
    status = requireText(status, "status");
    title = requireText(title, "title");
    author = normalize(author);
    description = normalize(description);
    version = normalize(version);
    tags = normalize(tags);
    body = normalize(body);
    summary = normalize(summary);
    contentNotes = normalize(contentNotes);
    references = normalize(references);
    notes = normalize(notes);
  }

  private static String requireText(String value, String fieldName) {
    String normalized = normalize(value);
    if (normalized.isBlank()) {
      throw new IllegalArgumentException(fieldName + " must not be blank");
    }
    return normalized;
  }

  private static String normalize(String value) {
    return Objects.toString(value, "").strip();
  }
}
