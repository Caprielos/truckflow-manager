package it.gabriele.truckflow.application.port.in.operation;

import it.gabriele.truckflow.domain.operation.TransportMission;
import it.gabriele.truckflow.domain.shared.Notes;

public interface PlanTransportMissionUseCase {

  TransportMission handle(Command command);

  record Command(
      String missionNumber,
      String shipmentNumber,
      String driverId,
      String vehicleCombinationId,
      String routePlanId,
      Notes notes) {}
}
