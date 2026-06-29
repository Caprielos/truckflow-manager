package it.gabriele.truckflow.domain.routeoptimization;

import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Money;
import java.time.Duration;
import java.util.List;
import java.util.Set;

/** Piano candidato di percorso ottimizzato con costi, tempi e vincoli. */
public record RouteOptimizationPlan(
    String planCode,
    String missionCode,
    Distance totalDistance,
    Duration estimatedDuration,
    Money estimatedCost,
    Set<RouteOptimizationObjective> objectives,
    List<RouteConstraint> constraints,
    double vehicleSaturationPercent,
    double driverSaturationPercent) {

  public RouteOptimizationPlan {
    planCode = normalize(planCode, "Il codice piano percorso è obbligatorio.");
    missionCode = normalize(missionCode, "Il codice missione è obbligatorio.");
    if (totalDistance == null || estimatedDuration == null || estimatedCost == null) {
      throw new IllegalArgumentException("Distanza, durata e costo stimato sono obbligatori.");
    }
    objectives = objectives == null ? Set.of() : Set.copyOf(objectives);
    constraints = constraints == null ? List.of() : List.copyOf(constraints);
    validatePercentage(vehicleSaturationPercent, "La saturazione mezzo non è valida.");
    validatePercentage(driverSaturationPercent, "La saturazione autista non è valida.");
  }

  public boolean hasBlockingConstraint() {
    return constraints.stream().anyMatch(RouteConstraint::blocksRoute);
  }

  public boolean isHighlySaturated() {
    return vehicleSaturationPercent >= 85.0 || driverSaturationPercent >= 85.0;
  }

  private static void validatePercentage(double value, String message) {
    if (Double.isNaN(value) || Double.isInfinite(value) || value < 0 || value > 100) {
      throw new IllegalArgumentException(message);
    }
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
