package it.gabriele.truckflow.infrastructure.memory.tachograph;

import it.gabriele.truckflow.application.port.out.tachograph.DrivingTimeViolationRepository;
import it.gabriele.truckflow.domain.tachograph.DrivingTimeViolation;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DrivingTimeViolation. */
public final class InMemoryDrivingTimeViolationRepository
    extends InMemoryRepository<DrivingTimeViolation> implements DrivingTimeViolationRepository {

  public InMemoryDrivingTimeViolationRepository() {
    super(violation -> violation.violationCode());
  }
}
