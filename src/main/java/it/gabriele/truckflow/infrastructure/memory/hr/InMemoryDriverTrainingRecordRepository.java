package it.gabriele.truckflow.infrastructure.memory.hr;

import it.gabriele.truckflow.application.port.out.hr.DriverTrainingRecordRepository;
import it.gabriele.truckflow.domain.hr.DriverTrainingRecord;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per formazione autisti. */
public final class InMemoryDriverTrainingRecordRepository
    extends InMemoryRepository<DriverTrainingRecord> implements DriverTrainingRecordRepository {

  public InMemoryDriverTrainingRecordRepository() {
    super(DriverTrainingRecord::getTrainingCode);
  }
}
