package it.gabriele.truckflow.infrastructure.memory.customs;

import it.gabriele.truckflow.application.port.out.customs.BorderCrossingRepository;
import it.gabriele.truckflow.domain.customs.BorderCrossing;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per attraversamenti confine. */
public final class InMemoryBorderCrossingRepository extends InMemoryRepository<BorderCrossing>
    implements BorderCrossingRepository {

  public InMemoryBorderCrossingRepository() {
    super(BorderCrossing::getCrossingCode);
  }
}
