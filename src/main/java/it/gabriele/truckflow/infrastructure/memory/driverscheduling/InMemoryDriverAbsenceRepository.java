package it.gabriele.truckflow.infrastructure.memory.driverscheduling;

import it.gabriele.truckflow.application.port.out.driverscheduling.DriverAbsenceRepository;
import it.gabriele.truckflow.domain.driverscheduling.DriverAbsence;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DriverAbsence. */
public final class InMemoryDriverAbsenceRepository extends InMemoryRepository<DriverAbsence>
    implements DriverAbsenceRepository {

  public InMemoryDriverAbsenceRepository() {
    super(absence -> absence.absenceCode());
  }
}
