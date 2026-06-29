package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DriverDisciplinaryRecordRepository;
import it.gabriele.truckflow.domain.driverscheduling.DriverDisciplinaryRecord;

/** Repository in memoria per DriverDisciplinaryRecord. */
public final class InMemoryDriverDisciplinaryRecordRepository
    extends InMemoryRepository<DriverDisciplinaryRecord>
    implements DriverDisciplinaryRecordRepository {

  public InMemoryDriverDisciplinaryRecordRepository() {
    super(record -> record.recordCode());
  }
}
