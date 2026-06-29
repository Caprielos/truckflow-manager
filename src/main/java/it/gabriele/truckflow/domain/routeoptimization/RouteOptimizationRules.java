package it.gabriele.truckflow.domain.routeoptimization;

/** Regole di validazione e ranking del percorso ottimizzato. */
public final class RouteOptimizationRules {

  private RouteOptimizationRules() {}

  public static boolean canDispatch(RouteOptimizationPlan plan) {
    if (plan == null) {
      throw new IllegalArgumentException("Il piano percorso è obbligatorio.");
    }
    return !plan.hasBlockingConstraint();
  }

  public static boolean requiresPlannerReview(RouteOptimizationPlan plan) {
    if (plan == null) {
      throw new IllegalArgumentException("Il piano percorso è obbligatorio.");
    }
    return plan.hasBlockingConstraint() || plan.isHighlySaturated();
  }
}
