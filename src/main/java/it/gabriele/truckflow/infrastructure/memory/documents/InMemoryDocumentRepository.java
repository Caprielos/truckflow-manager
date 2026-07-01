package it.gabriele.truckflow.infrastructure.memory.documents;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.documents.DocumentRepository;
import it.gabriele.truckflow.domain.documents.Document;
import it.gabriele.truckflow.domain.documents.DocumentCode;
import it.gabriele.truckflow.domain.documents.DocumentId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory implementation of the document repository port. */
public final class InMemoryDocumentRepository implements DocumentRepository {

  private final Map<DocumentId, Document> documentsById = new HashMap<>();
  private final Map<DocumentCode, DocumentId> idsByCode = new HashMap<>();

  @Override
  public Document save(Document document) {
    UseCaseValidationException.requireNonNull(document, "document");

    DocumentId existingId = idsByCode.get(document.code());
    if (existingId != null && !existingId.equals(document.id())) {
      throw new DuplicateResourceException("Document", document.code().value());
    }

    Document previousDocument = documentsById.put(document.id(), document);
    if (previousDocument != null && !previousDocument.code().equals(document.code())) {
      idsByCode.remove(previousDocument.code());
    }

    idsByCode.put(document.code(), document.id());
    return document;
  }

  @Override
  public Optional<Document> findById(DocumentId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(documentsById.get(id));
  }

  @Override
  public Optional<Document> findByCode(DocumentCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    DocumentId id = idsByCode.get(code);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public boolean existsById(DocumentId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return documentsById.containsKey(id);
  }

  @Override
  public boolean existsByCode(DocumentCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return idsByCode.containsKey(code);
  }
}
