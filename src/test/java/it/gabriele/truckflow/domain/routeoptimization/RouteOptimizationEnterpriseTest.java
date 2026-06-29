package it.gabriele.truckflow.domain.routeoptimization;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Money;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RouteOptimizationEnterpriseTest {

  @Test
  void shouldBlockRoutesWithLegalConstraintsAndReviewHighSaturation() {
    RouteConstraint blockingConstraint =
        new RouteConstraint(
            "con-001",
            RouteConstraintType.ADR_TUNNEL_RESTRICTION,
            RouteConstraintSeverity.LEGAL_BLOCKING,
            "ADR tunnel not allowed",
            false);
    RouteOptimizationPlan plan =
        new RouteOptimizationPlan(
            "plan-001",
            "mission-001",
            Distance.ofKilometers(680),
            Duration.ofHours(9),
            Money.of("980.00", "EUR"),
            Set.of(RouteOptimizationObjective.MINIMIZE_COST),
            List.of(blockingConstraint),
            92.0,
            70.0);

    assertTrue(plan.hasBlockingConstraint());
    assertFalse(RouteOptimizationRules.canDispatch(plan));
    assertTrue(RouteOptimizationRules.requiresPlannerReview(plan));
  }
}
