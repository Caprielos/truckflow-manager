package it.gabriele.truckflow.application.port.in.foodsafety;

import it.gabriele.truckflow.domain.foodsafety.FoodSafetyProfile;

public interface RegisterFoodSafetyProfileUseCase {
  FoodSafetyProfile handle(Command command);

  record Command(FoodSafetyProfile profile) {}
}
