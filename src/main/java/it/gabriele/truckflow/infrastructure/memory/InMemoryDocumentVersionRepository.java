package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DocumentVersionRepository;
import it.gabriele.truckflow.domain.document.DocumentVersion;

/** Repository in memoria per versioni documentali. */
public final class InMemoryDocumentVersionRepository extends InMemoryRepository<DocumentVersion>
    implements DocumentVersionRepository {

  public InMemoryDocumentVersionRepository() {
    super(version -> version.getDocumentNumber() + ":" + version.getVersionNumber());
  }
}
