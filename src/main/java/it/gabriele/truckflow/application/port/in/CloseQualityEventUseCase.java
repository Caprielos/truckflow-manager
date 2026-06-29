package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.quality.QualityEvent;
import java.time.Instant;

public interface CloseQualityEventUseCase {

  QualityEvent handle(Command command);

  record Command(String eventCode, Instant closedAt) {}
}
