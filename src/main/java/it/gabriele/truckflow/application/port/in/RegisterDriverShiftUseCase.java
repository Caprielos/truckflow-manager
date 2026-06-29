package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.driverscheduling.DriverShift;

public interface RegisterDriverShiftUseCase {
  DriverShift handle(Command command);

  record Command(DriverShift shift) {}
}
