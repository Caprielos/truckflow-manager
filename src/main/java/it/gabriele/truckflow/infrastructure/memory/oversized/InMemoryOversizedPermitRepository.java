package it.gabriele.truckflow.infrastructure.memory.oversized;

import it.gabriele.truckflow.application.port.out.OversizedPermitRepository;
import it.gabriele.truckflow.domain.oversized.OversizedPermit;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per OversizedPermit. */
public final class InMemoryOversizedPermitRepository extends InMemoryRepository<OversizedPermit>
    implements OversizedPermitRepository {

  public InMemoryOversizedPermitRepository() {
    super(permit -> permit.permitCode());
  }
}
