package it.gabriele.truckflow.infrastructure.memory.foodsafety;

import it.gabriele.truckflow.application.port.out.foodsafety.FoodSafetyProfileRepository;
import it.gabriele.truckflow.domain.foodsafety.FoodSafetyProfile;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per FoodSafetyProfile. */
public final class InMemoryFoodSafetyProfileRepository extends InMemoryRepository<FoodSafetyProfile>
    implements FoodSafetyProfileRepository {

  public InMemoryFoodSafetyProfileRepository() {
    super(profile -> profile.vehicleCode());
  }
}
