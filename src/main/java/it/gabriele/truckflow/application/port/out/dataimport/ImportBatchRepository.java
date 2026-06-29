package it.gabriele.truckflow.application.port.out.dataimport;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.dataimport.ImportBatch;

/** Repository port per ImportBatch. L'implementazione sarà in infrastructure. */
public interface ImportBatchRepository extends RepositoryPort<ImportBatch> {}
