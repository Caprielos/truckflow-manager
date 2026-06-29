package it.gabriele.truckflow.infrastructure.memory.dataimport;

import it.gabriele.truckflow.application.port.out.ImportBatchRepository;
import it.gabriele.truckflow.domain.dataimport.ImportBatch;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per ImportBatch. */
public final class InMemoryImportBatchRepository extends InMemoryRepository<ImportBatch>
    implements ImportBatchRepository {

  public InMemoryImportBatchRepository() {
    super(item -> item.getBatchCode());
  }
}
