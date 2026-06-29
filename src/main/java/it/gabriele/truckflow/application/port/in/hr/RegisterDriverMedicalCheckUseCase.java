package it.gabriele.truckflow.application.port.in.hr;

import it.gabriele.truckflow.domain.hr.DriverMedicalCheck;

public interface RegisterDriverMedicalCheckUseCase {

  DriverMedicalCheck handle(Command command);

  record Command(DriverMedicalCheck medicalCheck) {}
}
