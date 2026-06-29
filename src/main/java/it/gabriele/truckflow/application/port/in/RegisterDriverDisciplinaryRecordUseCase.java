package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.driverscheduling.DriverDisciplinaryRecord;

public interface RegisterDriverDisciplinaryRecordUseCase {
  DriverDisciplinaryRecord handle(Command command);

  record Command(DriverDisciplinaryRecord record) {}
}
