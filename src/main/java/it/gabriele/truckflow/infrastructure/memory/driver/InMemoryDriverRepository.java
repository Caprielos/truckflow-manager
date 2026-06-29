package it.gabriele.truckflow.infrastructure.memory.driver;

import it.gabriele.truckflow.application.port.out.DriverRepository;
import it.gabriele.truckflow.domain.driver.Driver;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per Driver. */
public final class InMemoryDriverRepository extends InMemoryRepository<Driver>
    implements DriverRepository {

  public InMemoryDriverRepository() {
    super(item -> item.getDriverCode());
  }
}
