package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.EvaluateRouteOptimizationPlanUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.RouteOptimizationPlanRepository;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.routeoptimization.*;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/** Implementazione default di EvaluateRouteOptimizationPlanUseCase. */
public final class DefaultEvaluateRouteOptimizationPlanUseCase
    implements EvaluateRouteOptimizationPlanUseCase {

  private final RouteOptimizationPlanRepository planRepository;
  private final AlertEventRepository alertRepository;

  public DefaultEvaluateRouteOptimizationPlanUseCase(
      RouteOptimizationPlanRepository planRepository, AlertEventRepository alertRepository) {
    this.planRepository = planRepository;
    this.alertRepository = alertRepository;
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    RouteOptimizationPlan plan =
        planRepository.getRequired(command.planCode(), "Piano ottimizzazione percorso");
    if (RouteOptimizationRules.canDispatch(plan))
      return EnterpriseValidationResult.passed("Piano percorso dispatchable.");
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "ROUTE",
            command.planCode(),
            "REVIEW",
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.HIGH,
            AlertSourceType.MISSION,
            "Piano percorso da revisionare",
            "Vincoli bloccanti o saturazione elevata.",
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(
        true, List.of("Piano percorso non dispatchable."), Optional.of(alert));
  }
}
