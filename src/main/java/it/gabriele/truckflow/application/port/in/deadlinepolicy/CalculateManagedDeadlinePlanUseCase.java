package it.gabriele.truckflow.application.port.in.deadlinepolicy;

import it.gabriele.truckflow.domain.deadlinepolicy.CombinedDeadlinePlan;
import it.gabriele.truckflow.domain.deadlinepolicy.DeadlineUsageSnapshot;
import it.gabriele.truckflow.domain.deadlinepolicy.ManagedDeadlineElementType;
import it.gabriele.truckflow.domain.regulation.EuropeanCountry;

/** Caso d'uso per calcolare un piano scadenze legale + tecnico. */
public interface CalculateManagedDeadlinePlanUseCase {

  CombinedDeadlinePlan handle(Command command);

  record Command(
      EuropeanCountry country,
      String manufacturer,
      String modelFamily,
      String ownerCode,
      ManagedDeadlineElementType elementType,
      DeadlineUsageSnapshot snapshot) {}
}
