package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DriverShiftRepository;
import it.gabriele.truckflow.domain.driverscheduling.DriverShift;

/** Repository in memoria per DriverShift. */
public final class InMemoryDriverShiftRepository extends InMemoryRepository<DriverShift>
    implements DriverShiftRepository {

  public InMemoryDriverShiftRepository() {
    super(shift -> shift.shiftCode());
  }
}
