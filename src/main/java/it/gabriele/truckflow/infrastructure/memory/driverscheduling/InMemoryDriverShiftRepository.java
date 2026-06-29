package it.gabriele.truckflow.infrastructure.memory.driverscheduling;

import it.gabriele.truckflow.application.port.out.DriverShiftRepository;
import it.gabriele.truckflow.domain.driverscheduling.DriverShift;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DriverShift. */
public final class InMemoryDriverShiftRepository extends InMemoryRepository<DriverShift>
    implements DriverShiftRepository {

  public InMemoryDriverShiftRepository() {
    super(shift -> shift.shiftCode());
  }
}
