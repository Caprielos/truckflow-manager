package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.TransportMissionRepository;
import it.gabriele.truckflow.domain.operation.TransportMission;

/** Repository in memoria per TransportMission. */
public final class InMemoryTransportMissionRepository extends InMemoryRepository<TransportMission> implements TransportMissionRepository {

    public InMemoryTransportMissionRepository() {
        super(item -> item.getMissionNumber());
    }
}
