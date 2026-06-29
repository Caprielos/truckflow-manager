package it.gabriele.truckflow.application.port.in.roadinspection;

import it.gabriele.truckflow.domain.roadinspection.RoadInspection;

public interface RegisterRoadInspectionUseCase {
  RoadInspection handle(Command command);

  record Command(RoadInspection inspection) {}
}
