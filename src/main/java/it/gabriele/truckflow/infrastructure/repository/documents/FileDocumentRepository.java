package it.gabriele.truckflow.infrastructure.repository.documents;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.documents.DocumentRepository;
import it.gabriele.truckflow.domain.documents.Document;
import it.gabriele.truckflow.domain.documents.DocumentCode;
import it.gabriele.truckflow.domain.documents.DocumentId;
import it.gabriele.truckflow.infrastructure.repository.InfrastructureRepositoryAdapter;
import it.gabriele.truckflow.infrastructure.repository.file.FileRepositoryStorage;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/** File-backed implementation of the logical document repository port. */
public final class FileDocumentRepository
    implements DocumentRepository, InfrastructureRepositoryAdapter {

  private final FileRepositoryStorage<DocumentPersistenceRecord> storage;
  private final DocumentPersistenceMapper mapper;

  public FileDocumentRepository(Path storageFile) {
    this(storageFile, new DocumentPersistenceMapper());
  }

  public FileDocumentRepository(Path storageFile, DocumentPersistenceMapper mapper) {
    UseCaseValidationException.requireNonNull(mapper, "mapper");
    this.storage =
        new FileRepositoryStorage<>(storageFile, new DocumentFileRecordCodec(), "document");
    this.mapper = mapper;
  }

  @Override
  public String adapterName() {
    return "file-document-repository";
  }

  @Override
  public String implementedPortName() {
    return DocumentRepository.class.getName();
  }

  @Override
  public Document save(Document document) {
    UseCaseValidationException.requireNonNull(document, "document");

    Map<DocumentId, Document> documents = loadAllById();
    Optional<Document> duplicate =
        documents.values().stream()
            .filter(existing -> existing.code().equals(document.code()))
            .filter(existing -> !existing.id().equals(document.id()))
            .findFirst();

    if (duplicate.isPresent()) {
      throw new DuplicateResourceException("Document", document.code().value());
    }

    documents.put(document.id(), document);
    writeAll(documents);
    return document;
  }

  @Override
  public Optional<Document> findById(DocumentId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(loadAllById().get(id));
  }

  @Override
  public Optional<Document> findByCode(DocumentCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return loadAllById().values().stream()
        .filter(document -> document.code().equals(code))
        .findFirst();
  }

  @Override
  public boolean existsById(DocumentId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return loadAllById().containsKey(id);
  }

  @Override
  public boolean existsByCode(DocumentCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return findByCode(code).isPresent();
  }

  private Map<DocumentId, Document> loadAllById() {
    Map<DocumentId, Document> documents = new LinkedHashMap<>();
    for (DocumentPersistenceRecord record : storage.readAll()) {
      Document document = mapper.toDomain(record);
      documents.put(document.id(), document);
    }
    return documents;
  }

  private void writeAll(Map<DocumentId, Document> documents) {
    storage.writeAll(
        documents.values().stream().map(mapper::toPersistence).toList(),
        Comparator.comparing(DocumentPersistenceRecord::code));
  }
}
