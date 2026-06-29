package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.FoodSafetyProfileRepository;
import it.gabriele.truckflow.domain.foodsafety.FoodSafetyProfile;

/** Repository in memoria per FoodSafetyProfile. */
public final class InMemoryFoodSafetyProfileRepository extends InMemoryRepository<FoodSafetyProfile>
    implements FoodSafetyProfileRepository {

  public InMemoryFoodSafetyProfileRepository() {
    super(profile -> profile.vehicleCode());
  }
}
