package it.gabriele.truckflow.infrastructure.memory.vehicles;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleCombinationRepository;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombination;
import it.gabriele.truckflow.domain.vehicles.combination.VehicleCombinationId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory implementation of the vehicle combination repository port. */
public final class InMemoryVehicleCombinationRepository implements VehicleCombinationRepository {

  private final Map<VehicleCombinationId, VehicleCombination> vehicleCombinationsById =
      new HashMap<>();

  @Override
  public VehicleCombination save(VehicleCombination vehicleCombination) {
    UseCaseValidationException.requireNonNull(vehicleCombination, "vehicleCombination");
    vehicleCombinationsById.put(vehicleCombination.id(), vehicleCombination);
    return vehicleCombination;
  }

  @Override
  public Optional<VehicleCombination> findById(VehicleCombinationId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(vehicleCombinationsById.get(id));
  }

  @Override
  public boolean existsById(VehicleCombinationId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return vehicleCombinationsById.containsKey(id);
  }
}
