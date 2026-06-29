package it.gabriele.truckflow.infrastructure.memory.document;

import it.gabriele.truckflow.application.port.out.DeliveryNoteRepository;
import it.gabriele.truckflow.domain.document.DeliveryNote;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DeliveryNote. */
public final class InMemoryDeliveryNoteRepository extends InMemoryRepository<DeliveryNote>
    implements DeliveryNoteRepository {

  public InMemoryDeliveryNoteRepository() {
    super(item -> item.getDocumentNumber());
  }
}
