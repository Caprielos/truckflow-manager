package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.RoadInspectionRepository;
import it.gabriele.truckflow.domain.roadinspection.RoadInspection;

/** Repository in memoria per RoadInspection. */
public final class InMemoryRoadInspectionRepository extends InMemoryRepository<RoadInspection>
    implements RoadInspectionRepository {

  public InMemoryRoadInspectionRepository() {
    super(inspection -> inspection.inspectionCode());
  }
}
