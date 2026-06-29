package it.gabriele.truckflow.infrastructure.memory.operation;

import it.gabriele.truckflow.application.port.out.operation.TransportMissionRepository;
import it.gabriele.truckflow.domain.operation.TransportMission;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per TransportMission. */
public final class InMemoryTransportMissionRepository extends InMemoryRepository<TransportMission>
    implements TransportMissionRepository {

  public InMemoryTransportMissionRepository() {
    super(item -> item.getMissionNumber());
  }
}
