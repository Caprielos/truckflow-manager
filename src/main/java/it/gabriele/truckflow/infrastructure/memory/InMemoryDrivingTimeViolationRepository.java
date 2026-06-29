package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DrivingTimeViolationRepository;
import it.gabriele.truckflow.domain.tachograph.DrivingTimeViolation;

/** Repository in memoria per DrivingTimeViolation. */
public final class InMemoryDrivingTimeViolationRepository
    extends InMemoryRepository<DrivingTimeViolation> implements DrivingTimeViolationRepository {

  public InMemoryDrivingTimeViolationRepository() {
    super(violation -> violation.violationCode());
  }
}
