package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.customs.BorderCrossing;

public interface RecordBorderCrossingUseCase {

  BorderCrossing handle(Command command);

  record Command(BorderCrossing crossing) {}
}
