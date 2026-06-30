package it.gabriele.truckflow.domain.documents;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Document {

  private final DocumentId id;
  private final DocumentCode code;
  private DocumentType type;
  private DocumentCategory category;
  private DocumentStatus status;
  private DocumentMetadata metadata;
  private DocumentContent content;
  private Set<DocumentReference> references;
  private String notes;

  public Document(
      DocumentId id,
      DocumentCode code,
      DocumentType type,
      DocumentCategory category,
      DocumentStatus status,
      DocumentMetadata metadata,
      DocumentContent content,
      Set<DocumentReference> references,
      String notes) {
    this.id = id == null ? DocumentId.random() : id;
    this.code = DocumentValidation.requireNonNull(code, "code");
    this.type = DocumentValidation.requireNonNull(type, "type");
    this.category = DocumentValidation.requireNonNull(category, "category");
    this.status = DocumentValidation.requireNonNull(status, "status");
    this.metadata = DocumentValidation.requireNonNull(metadata, "metadata");
    this.content = content == null ? DocumentContent.empty() : content;
    this.references = validateReferences(references);
    this.notes = DocumentValidation.normalize(notes);
  }

  public DocumentId id() {
    return id;
  }

  public DocumentCode code() {
    return code;
  }

  public DocumentType type() {
    return type;
  }

  public DocumentCategory category() {
    return category;
  }

  public DocumentStatus status() {
    return status;
  }

  public DocumentMetadata metadata() {
    return metadata;
  }

  public DocumentContent content() {
    return content;
  }

  public Set<DocumentReference> references() {
    return Set.copyOf(references);
  }

  public String notes() {
    return notes;
  }

  public boolean isDraft() {
    return status == DocumentStatus.DRAFT;
  }

  public boolean isActive() {
    return status == DocumentStatus.ACTIVE;
  }

  public boolean isArchived() {
    return status == DocumentStatus.ARCHIVED;
  }

  public boolean hasLogicalContent() {
    return content.hasContent();
  }

  public boolean hasReference(DocumentReferenceType referenceType, String referencedId) {
    return references.stream()
        .anyMatch(reference -> reference.references(referenceType, referencedId));
  }

  public void changeClassification(DocumentType type, DocumentCategory category) {
    DocumentType updatedType = DocumentValidation.requireNonNull(type, "type");
    DocumentCategory updatedCategory = DocumentValidation.requireNonNull(category, "category");

    this.type = updatedType;
    this.category = updatedCategory;
  }

  public void replaceMetadata(DocumentMetadata metadata) {
    this.metadata = DocumentValidation.requireNonNull(metadata, "metadata");
  }

  public void replaceContent(DocumentContent content) {
    this.content = content == null ? DocumentContent.empty() : content;
  }

  public void replaceReferences(Set<DocumentReference> references) {
    this.references = validateReferences(references);
  }

  public void addReference(DocumentReference reference) {
    reference = DocumentValidation.requireNonNull(reference, "reference");
    references =
        Stream.concat(references.stream(), Stream.of(reference))
            .collect(Collectors.toUnmodifiableSet());
  }

  public void removeReference(DocumentReference reference) {
    DocumentReference referenceToRemove = DocumentValidation.requireNonNull(reference, "reference");
    references =
        references.stream()
            .filter(existingReference -> !existingReference.equals(referenceToRemove))
            .collect(Collectors.toUnmodifiableSet());
  }

  public void updateNotes(String notes) {
    this.notes = DocumentValidation.normalize(notes);
  }

  public void activate() {
    status = DocumentStatus.ACTIVE;
  }

  public void suspend() {
    status = DocumentStatus.SUSPENDED;
  }

  public void archive() {
    status = DocumentStatus.ARCHIVED;
  }

  private static Set<DocumentReference> validateReferences(Set<DocumentReference> references) {
    if (references == null || references.isEmpty()) {
      return Set.of();
    }

    DocumentValidation.requireNoNullElements(references, "references");
    return Set.copyOf(references);
  }
}
