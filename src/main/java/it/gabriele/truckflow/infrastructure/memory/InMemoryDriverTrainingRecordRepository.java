package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DriverTrainingRecordRepository;
import it.gabriele.truckflow.domain.hr.DriverTrainingRecord;

/** Repository in memoria per formazione autisti. */
public final class InMemoryDriverTrainingRecordRepository
    extends InMemoryRepository<DriverTrainingRecord> implements DriverTrainingRecordRepository {

  public InMemoryDriverTrainingRecordRepository() {
    super(DriverTrainingRecord::getTrainingCode);
  }
}
