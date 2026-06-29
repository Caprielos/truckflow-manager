package it.gabriele.truckflow.infrastructure.memory.roadinspection;

import it.gabriele.truckflow.application.port.out.roadinspection.RoadInspectionRepository;
import it.gabriele.truckflow.domain.roadinspection.RoadInspection;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per RoadInspection. */
public final class InMemoryRoadInspectionRepository extends InMemoryRepository<RoadInspection>
    implements RoadInspectionRepository {

  public InMemoryRoadInspectionRepository() {
    super(inspection -> inspection.inspectionCode());
  }
}
