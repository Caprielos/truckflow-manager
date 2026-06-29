package it.gabriele.truckflow.application.port.in.driverscheduling;

import it.gabriele.truckflow.domain.driverscheduling.DriverAbsence;

public interface RegisterDriverAbsenceUseCase {
  DriverAbsence handle(Command command);

  record Command(DriverAbsence absence) {}
}
