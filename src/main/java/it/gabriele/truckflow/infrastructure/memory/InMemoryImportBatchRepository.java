package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.ImportBatchRepository;
import it.gabriele.truckflow.domain.dataimport.ImportBatch;

/** Repository in memoria per ImportBatch. */
public final class InMemoryImportBatchRepository extends InMemoryRepository<ImportBatch>
    implements ImportBatchRepository {

  public InMemoryImportBatchRepository() {
    super(item -> item.getBatchCode());
  }
}
