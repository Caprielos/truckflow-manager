package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.MissionEconomicsRepository;
import it.gabriele.truckflow.domain.economics.MissionEconomics;

/** Repository in memoria per MissionEconomics. */
public final class InMemoryMissionEconomicsRepository extends InMemoryRepository<MissionEconomics> implements MissionEconomicsRepository {

    public InMemoryMissionEconomicsRepository() {
        super(item -> item.getMissionNumber());
    }
}
