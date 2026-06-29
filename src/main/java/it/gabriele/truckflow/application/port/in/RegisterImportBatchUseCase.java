package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.dataimport.ImportBatch;

public interface RegisterImportBatchUseCase {

  ImportBatch handle(Command command);

  record Command(ImportBatch batch) {}
}
