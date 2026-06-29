package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.operation.TransportMission;

public interface CloseTransportMissionUseCase {

  TransportMission handle(Command command);

  record Command(String missionNumber) {}
}
