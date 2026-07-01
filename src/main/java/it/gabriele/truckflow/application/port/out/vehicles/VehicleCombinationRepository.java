package it.gabriele.truckflow.application.port.out.vehicles;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombination;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombinationId;
import java.util.Optional;

/** Outbound repository port used by vehicle combination use cases. */
public interface VehicleCombinationRepository extends RepositoryPort {

  VehicleCombination save(VehicleCombination vehicleCombination);

  Optional<VehicleCombination> findById(VehicleCombinationId id);

  boolean existsById(VehicleCombinationId id);
}
