package it.gabriele.truckflow.infrastructure.memory.economics;

import it.gabriele.truckflow.application.port.out.economics.MissionEconomicsRepository;
import it.gabriele.truckflow.domain.economics.MissionEconomics;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per MissionEconomics. */
public final class InMemoryMissionEconomicsRepository extends InMemoryRepository<MissionEconomics>
    implements MissionEconomicsRepository {

  public InMemoryMissionEconomicsRepository() {
    super(item -> item.getMissionNumber());
  }
}
