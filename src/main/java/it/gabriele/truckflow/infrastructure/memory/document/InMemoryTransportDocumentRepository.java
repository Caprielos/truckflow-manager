package it.gabriele.truckflow.infrastructure.memory.document;

import it.gabriele.truckflow.application.port.out.document.TransportDocumentRepository;
import it.gabriele.truckflow.domain.document.TransportDocument;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per TransportDocument. */
public final class InMemoryTransportDocumentRepository extends InMemoryRepository<TransportDocument>
    implements TransportDocumentRepository {

  public InMemoryTransportDocumentRepository() {
    super(item -> item.getDocumentNumber());
  }
}
