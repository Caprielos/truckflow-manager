package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.LoadSecuringChecklistRepository;
import it.gabriele.truckflow.domain.loadsecurity.LoadSecuringChecklist;

/** Repository in memoria per LoadSecuringChecklist. */
public final class InMemoryLoadSecuringChecklistRepository extends InMemoryRepository<LoadSecuringChecklist> implements LoadSecuringChecklistRepository {

    public InMemoryLoadSecuringChecklistRepository() {
        super(item -> "CHECKLIST-" + System.identityHashCode(item));
    }
}
