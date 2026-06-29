package it.gabriele.truckflow.application.port.in.tachograph;

import it.gabriele.truckflow.domain.tachograph.DrivingTimeViolation;

public interface RegisterDrivingTimeViolationUseCase {
  DrivingTimeViolation handle(Command command);

  record Command(DrivingTimeViolation violation) {}
}
