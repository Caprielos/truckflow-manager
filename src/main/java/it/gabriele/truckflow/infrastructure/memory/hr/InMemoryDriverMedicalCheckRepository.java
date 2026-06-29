package it.gabriele.truckflow.infrastructure.memory.hr;

import it.gabriele.truckflow.application.port.out.DriverMedicalCheckRepository;
import it.gabriele.truckflow.domain.hr.DriverMedicalCheck;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per visite mediche autisti. */
public final class InMemoryDriverMedicalCheckRepository
    extends InMemoryRepository<DriverMedicalCheck> implements DriverMedicalCheckRepository {

  public InMemoryDriverMedicalCheckRepository() {
    super(DriverMedicalCheck::getCheckCode);
  }
}
