package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.TelematicsSnapshotRepository;
import it.gabriele.truckflow.domain.telematics.TelematicsSnapshot;

/** Repository in memoria per TelematicsSnapshot. */
public final class InMemoryTelematicsSnapshotRepository
    extends InMemoryRepository<TelematicsSnapshot> implements TelematicsSnapshotRepository {

  public InMemoryTelematicsSnapshotRepository() {
    super(item -> item.getVehicleFleetNumber() + "@" + item.getRecordedAt());
  }
}
