package it.gabriele.truckflow.infrastructure.repository.documents;

import it.gabriele.truckflow.domain.documents.Document;
import it.gabriele.truckflow.domain.documents.DocumentCategory;
import it.gabriele.truckflow.domain.documents.DocumentCode;
import it.gabriele.truckflow.domain.documents.DocumentContent;
import it.gabriele.truckflow.domain.documents.DocumentId;
import it.gabriele.truckflow.domain.documents.DocumentMetadata;
import it.gabriele.truckflow.domain.documents.DocumentReference;
import it.gabriele.truckflow.domain.documents.DocumentReferenceType;
import it.gabriele.truckflow.domain.documents.DocumentStatus;
import it.gabriele.truckflow.domain.documents.DocumentType;
import it.gabriele.truckflow.infrastructure.exception.MappingException;
import it.gabriele.truckflow.infrastructure.mapping.PersistenceMapper;
import it.gabriele.truckflow.infrastructure.repository.file.FileRepositoryText;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/** Maps logical documents to and from the file-backed persistence record used by Punto 7F. */
public final class DocumentPersistenceMapper
    implements PersistenceMapper<Document, DocumentPersistenceRecord> {

  @Override
  public DocumentPersistenceRecord toPersistence(Document domainModel) {
    Objects.requireNonNull(domainModel, "domainModel must not be null");

    DocumentMetadata metadata = domainModel.metadata();
    DocumentContent content = domainModel.content();

    return new DocumentPersistenceRecord(
        domainModel.id().value().toString(),
        domainModel.code().value(),
        domainModel.type().name(),
        domainModel.category().name(),
        domainModel.status().name(),
        metadata.title(),
        metadata.author(),
        metadata.description(),
        metadata.version(),
        FileRepositoryText.encodedStrings(metadata.tags()),
        content.body(),
        content.summary(),
        content.notes(),
        FileRepositoryText.encodedStructures(
            domainModel.references(),
            reference ->
                List.of(
                    reference.referenceType().name(), reference.referencedId(), reference.notes())),
        domainModel.notes());
  }

  @Override
  public Document toDomain(DocumentPersistenceRecord persistenceModel) {
    Objects.requireNonNull(persistenceModel, "persistenceModel must not be null");

    try {
      return new Document(
          new DocumentId(UUID.fromString(persistenceModel.id())),
          DocumentCode.of(persistenceModel.code()),
          DocumentType.valueOf(persistenceModel.type()),
          DocumentCategory.valueOf(persistenceModel.category()),
          DocumentStatus.valueOf(persistenceModel.status()),
          metadataFrom(persistenceModel),
          contentFrom(persistenceModel),
          referencesFrom(persistenceModel),
          persistenceModel.notes());
    } catch (IllegalArgumentException exception) {
      throw new MappingException("Unable to rebuild Document from persistence record.", exception);
    }
  }

  private static DocumentMetadata metadataFrom(DocumentPersistenceRecord record) {
    return new DocumentMetadata(
        record.title(),
        record.author(),
        record.description(),
        record.version(),
        FileRepositoryText.parseEncodedStrings(record.tags()));
  }

  private static DocumentContent contentFrom(DocumentPersistenceRecord record) {
    return new DocumentContent(record.body(), record.summary(), record.contentNotes());
  }

  private static java.util.Set<DocumentReference> referencesFrom(DocumentPersistenceRecord record) {
    return FileRepositoryText.parseEncodedStructures(record.references()).stream()
        .map(DocumentPersistenceMapper::referenceFrom)
        .collect(Collectors.toUnmodifiableSet());
  }

  private static DocumentReference referenceFrom(List<String> fields) {
    if (fields.size() != 3) {
      throw new MappingException("Invalid document reference persistence field count.");
    }
    return new DocumentReference(
        DocumentReferenceType.valueOf(fields.get(0)), fields.get(1), fields.get(2));
  }
}
