package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.livestock.LivestockTripPlan;

public interface RegisterLivestockTripPlanUseCase {
  LivestockTripPlan handle(Command command);

  record Command(LivestockTripPlan tripPlan) {}
}
