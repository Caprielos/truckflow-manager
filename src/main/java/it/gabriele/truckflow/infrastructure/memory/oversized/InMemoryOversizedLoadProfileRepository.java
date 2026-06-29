package it.gabriele.truckflow.infrastructure.memory.oversized;

import it.gabriele.truckflow.application.port.out.OversizedLoadProfileRepository;
import it.gabriele.truckflow.domain.oversized.OversizedLoadProfile;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per OversizedLoadProfile. */
public final class InMemoryOversizedLoadProfileRepository
    extends InMemoryRepository<OversizedLoadProfile> implements OversizedLoadProfileRepository {

  public InMemoryOversizedLoadProfileRepository() {
    super(load -> load.loadCode());
  }
}
