package it.gabriele.truckflow.infrastructure.memory.loadsecurity;

import it.gabriele.truckflow.application.port.out.LoadSecuringChecklistRepository;
import it.gabriele.truckflow.domain.loadsecurity.LoadSecuringChecklist;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per LoadSecuringChecklist. */
public final class InMemoryLoadSecuringChecklistRepository
    extends InMemoryRepository<LoadSecuringChecklist> implements LoadSecuringChecklistRepository {

  public InMemoryLoadSecuringChecklistRepository() {
    super(item -> "CHECKLIST-" + System.identityHashCode(item));
  }
}
