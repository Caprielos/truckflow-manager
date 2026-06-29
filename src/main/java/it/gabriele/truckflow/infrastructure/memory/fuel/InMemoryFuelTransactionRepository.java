package it.gabriele.truckflow.infrastructure.memory.fuel;

import it.gabriele.truckflow.application.port.out.fuel.FuelTransactionRepository;
import it.gabriele.truckflow.domain.fuel.FuelTransaction;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per FuelTransaction. */
public final class InMemoryFuelTransactionRepository extends InMemoryRepository<FuelTransaction>
    implements FuelTransactionRepository {

  public InMemoryFuelTransactionRepository() {
    super(item -> item.getVehicleFleetNumber() + "@" + item.getOccurredAt());
  }
}
