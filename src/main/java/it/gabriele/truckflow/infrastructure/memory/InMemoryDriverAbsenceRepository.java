package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DriverAbsenceRepository;
import it.gabriele.truckflow.domain.driverscheduling.DriverAbsence;

/** Repository in memoria per DriverAbsence. */
public final class InMemoryDriverAbsenceRepository extends InMemoryRepository<DriverAbsence>
    implements DriverAbsenceRepository {

  public InMemoryDriverAbsenceRepository() {
    super(absence -> absence.absenceCode());
  }
}
