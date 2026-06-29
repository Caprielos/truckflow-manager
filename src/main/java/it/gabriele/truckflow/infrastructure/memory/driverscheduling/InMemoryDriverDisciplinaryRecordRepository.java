package it.gabriele.truckflow.infrastructure.memory.driverscheduling;

import it.gabriele.truckflow.application.port.out.driverscheduling.DriverDisciplinaryRecordRepository;
import it.gabriele.truckflow.domain.driverscheduling.DriverDisciplinaryRecord;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DriverDisciplinaryRecord. */
public final class InMemoryDriverDisciplinaryRecordRepository
    extends InMemoryRepository<DriverDisciplinaryRecord>
    implements DriverDisciplinaryRecordRepository {

  public InMemoryDriverDisciplinaryRecordRepository() {
    super(record -> record.recordCode());
  }
}
