package it.gabriele.truckflow.application.port.out.fleet;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.fleet.Vehicle;

/** Repository port per Vehicle. L'implementazione sarà in infrastructure. */
public interface VehicleRepository extends RepositoryPort<Vehicle> {}
