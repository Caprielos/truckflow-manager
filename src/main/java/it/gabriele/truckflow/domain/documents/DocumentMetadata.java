package it.gabriele.truckflow.domain.documents;

import java.util.Set;
import java.util.stream.Collectors;

public record DocumentMetadata(
    String title, String author, String description, String version, Set<String> tags) {

  public DocumentMetadata {
    title = DocumentValidation.requireText(title, "title");
    author = DocumentValidation.normalize(author);
    description = DocumentValidation.normalize(description);
    version = DocumentValidation.normalize(version);
    tags = normalizeTags(tags);
  }

  public static DocumentMetadata minimal(String title) {
    return new DocumentMetadata(title, "", "", "", Set.of());
  }

  public boolean hasTag(String tag) {
    String normalized = DocumentValidation.normalize(tag).toLowerCase();
    return !normalized.isBlank() && tags.contains(normalized);
  }

  private static Set<String> normalizeTags(Set<String> tags) {
    if (tags == null || tags.isEmpty()) {
      return Set.of();
    }

    DocumentValidation.requireNoNullElements(tags, "tags");
    return tags.stream()
        .map(DocumentValidation::normalize)
        .filter(tag -> !tag.isBlank())
        .map(String::toLowerCase)
        .collect(Collectors.toUnmodifiableSet());
  }
}
