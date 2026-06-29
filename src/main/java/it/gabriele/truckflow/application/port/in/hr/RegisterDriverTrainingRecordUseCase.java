package it.gabriele.truckflow.application.port.in.hr;

import it.gabriele.truckflow.domain.hr.DriverTrainingRecord;

public interface RegisterDriverTrainingRecordUseCase {

  DriverTrainingRecord handle(Command command);

  record Command(DriverTrainingRecord trainingRecord) {}
}
