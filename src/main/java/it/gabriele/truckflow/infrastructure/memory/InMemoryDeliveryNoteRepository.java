package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DeliveryNoteRepository;
import it.gabriele.truckflow.domain.document.DeliveryNote;

/** Repository in memoria per DeliveryNote. */
public final class InMemoryDeliveryNoteRepository extends InMemoryRepository<DeliveryNote> implements DeliveryNoteRepository {

    public InMemoryDeliveryNoteRepository() {
        super(item -> item.getDocumentNumber());
    }
}
