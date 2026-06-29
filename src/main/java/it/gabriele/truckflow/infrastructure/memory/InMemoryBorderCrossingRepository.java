package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.BorderCrossingRepository;
import it.gabriele.truckflow.domain.customs.BorderCrossing;

/** Repository in memoria per attraversamenti confine. */
public final class InMemoryBorderCrossingRepository extends InMemoryRepository<BorderCrossing>
    implements BorderCrossingRepository {

  public InMemoryBorderCrossingRepository() {
    super(BorderCrossing::getCrossingCode);
  }
}
