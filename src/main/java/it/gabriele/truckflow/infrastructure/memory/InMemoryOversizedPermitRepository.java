package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.OversizedPermitRepository;
import it.gabriele.truckflow.domain.oversized.OversizedPermit;

/** Repository in memoria per OversizedPermit. */
public final class InMemoryOversizedPermitRepository extends InMemoryRepository<OversizedPermit>
    implements OversizedPermitRepository {

  public InMemoryOversizedPermitRepository() {
    super(permit -> permit.permitCode());
  }
}
