package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.TransportDocumentRepository;
import it.gabriele.truckflow.domain.document.TransportDocument;

/** Repository in memoria per TransportDocument. */
public final class InMemoryTransportDocumentRepository extends InMemoryRepository<TransportDocument>
    implements TransportDocumentRepository {

  public InMemoryTransportDocumentRepository() {
    super(item -> item.getDocumentNumber());
  }
}
