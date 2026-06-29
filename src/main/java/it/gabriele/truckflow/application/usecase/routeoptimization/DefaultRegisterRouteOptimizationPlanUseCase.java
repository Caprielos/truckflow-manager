package it.gabriele.truckflow.application.usecase.routeoptimization;

import it.gabriele.truckflow.application.port.in.routeoptimization.RegisterRouteOptimizationPlanUseCase;
import it.gabriele.truckflow.application.port.out.routeoptimization.RouteOptimizationPlanRepository;
import it.gabriele.truckflow.domain.routeoptimization.RouteOptimizationPlan;
import java.util.Objects;

/** Implementazione default di RegisterRouteOptimizationPlanUseCase. */
public final class DefaultRegisterRouteOptimizationPlanUseCase
    implements RegisterRouteOptimizationPlanUseCase {

  private final RouteOptimizationPlanRepository repository;

  public DefaultRegisterRouteOptimizationPlanUseCase(RouteOptimizationPlanRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public RouteOptimizationPlan handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    RouteOptimizationPlan aggregate =
        Objects.requireNonNull(command.plan(), "Il piano ottimizzazione percorso è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
