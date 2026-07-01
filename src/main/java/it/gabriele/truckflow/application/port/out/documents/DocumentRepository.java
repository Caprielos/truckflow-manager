package it.gabriele.truckflow.application.port.out.documents;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.documents.Document;
import it.gabriele.truckflow.domain.documents.DocumentCode;
import it.gabriele.truckflow.domain.documents.DocumentId;
import java.util.Optional;

/** Outbound repository port used by document use cases. */
public interface DocumentRepository extends RepositoryPort {

  Document save(Document document);

  Optional<Document> findById(DocumentId id);

  Optional<Document> findByCode(DocumentCode code);

  boolean existsById(DocumentId id);

  boolean existsByCode(DocumentCode code);
}
