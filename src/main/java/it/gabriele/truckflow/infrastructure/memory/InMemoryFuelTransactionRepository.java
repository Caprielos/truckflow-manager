package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.FuelTransactionRepository;
import it.gabriele.truckflow.domain.fuel.FuelTransaction;

/** Repository in memoria per FuelTransaction. */
public final class InMemoryFuelTransactionRepository extends InMemoryRepository<FuelTransaction>
    implements FuelTransactionRepository {

  public InMemoryFuelTransactionRepository() {
    super(item -> item.getVehicleFleetNumber() + "@" + item.getOccurredAt());
  }
}
