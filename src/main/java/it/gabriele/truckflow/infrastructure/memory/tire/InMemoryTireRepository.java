package it.gabriele.truckflow.infrastructure.memory.tire;

import it.gabriele.truckflow.application.port.out.TireRepository;
import it.gabriele.truckflow.domain.tire.Tire;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per Tire. */
public final class InMemoryTireRepository extends InMemoryRepository<Tire>
    implements TireRepository {

  public InMemoryTireRepository() {
    super(item -> item.getTireCode());
  }
}
