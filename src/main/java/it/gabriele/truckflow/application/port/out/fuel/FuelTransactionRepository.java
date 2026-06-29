package it.gabriele.truckflow.application.port.out.fuel;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.fuel.FuelTransaction;

/** Repository port per FuelTransaction. L'implementazione sarà in infrastructure. */
public interface FuelTransactionRepository extends RepositoryPort<FuelTransaction> {}
