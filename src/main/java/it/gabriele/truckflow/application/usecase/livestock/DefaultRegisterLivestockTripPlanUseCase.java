package it.gabriele.truckflow.application.usecase.livestock;

import it.gabriele.truckflow.application.port.in.RegisterLivestockTripPlanUseCase;
import it.gabriele.truckflow.application.port.out.LivestockTripPlanRepository;
import it.gabriele.truckflow.domain.livestock.LivestockTripPlan;
import java.util.Objects;

/** Implementazione default di RegisterLivestockTripPlanUseCase. */
public final class DefaultRegisterLivestockTripPlanUseCase
    implements RegisterLivestockTripPlanUseCase {

  private final LivestockTripPlanRepository repository;

  public DefaultRegisterLivestockTripPlanUseCase(LivestockTripPlanRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public LivestockTripPlan handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    LivestockTripPlan aggregate =
        Objects.requireNonNull(command.tripPlan(), "Il piano viaggio animali è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
