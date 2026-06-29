package it.gabriele.truckflow.infrastructure.memory.telematics;

import it.gabriele.truckflow.application.port.out.telematics.TelematicsSnapshotRepository;
import it.gabriele.truckflow.domain.telematics.TelematicsSnapshot;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per TelematicsSnapshot. */
public final class InMemoryTelematicsSnapshotRepository
    extends InMemoryRepository<TelematicsSnapshot> implements TelematicsSnapshotRepository {

  public InMemoryTelematicsSnapshotRepository() {
    super(item -> item.getVehicleFleetNumber() + "@" + item.getRecordedAt());
  }
}
