package it.gabriele.truckflow.infrastructure.memory.waste;

import it.gabriele.truckflow.application.port.out.WasteTransportDocumentRepository;
import it.gabriele.truckflow.domain.waste.WasteTransportDocument;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per WasteTransportDocument. */
public final class InMemoryWasteTransportDocumentRepository
    extends InMemoryRepository<WasteTransportDocument> implements WasteTransportDocumentRepository {

  public InMemoryWasteTransportDocumentRepository() {
    super(document -> document.documentCode());
  }
}
