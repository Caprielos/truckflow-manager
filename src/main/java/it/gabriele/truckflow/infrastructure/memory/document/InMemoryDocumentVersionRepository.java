package it.gabriele.truckflow.infrastructure.memory.document;

import it.gabriele.truckflow.application.port.out.document.DocumentVersionRepository;
import it.gabriele.truckflow.domain.document.DocumentVersion;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per versioni documentali. */
public final class InMemoryDocumentVersionRepository extends InMemoryRepository<DocumentVersion>
    implements DocumentVersionRepository {

  public InMemoryDocumentVersionRepository() {
    super(version -> version.getDocumentNumber() + ":" + version.getVersionNumber());
  }
}
