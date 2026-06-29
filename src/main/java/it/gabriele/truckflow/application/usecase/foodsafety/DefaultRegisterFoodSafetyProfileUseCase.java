package it.gabriele.truckflow.application.usecase.foodsafety;

import it.gabriele.truckflow.application.port.in.foodsafety.RegisterFoodSafetyProfileUseCase;
import it.gabriele.truckflow.application.port.out.FoodSafetyProfileRepository;
import it.gabriele.truckflow.domain.foodsafety.FoodSafetyProfile;
import java.util.Objects;

/** Implementazione default di RegisterFoodSafetyProfileUseCase. */
public final class DefaultRegisterFoodSafetyProfileUseCase
    implements RegisterFoodSafetyProfileUseCase {

  private final FoodSafetyProfileRepository repository;

  public DefaultRegisterFoodSafetyProfileUseCase(FoodSafetyProfileRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public FoodSafetyProfile handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    FoodSafetyProfile aggregate =
        Objects.requireNonNull(command.profile(), "Il profilo alimentare è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
