package it.gabriele.truckflow.application.port.in.quality;

import it.gabriele.truckflow.domain.quality.QualityEvent;

public interface OpenQualityEventUseCase {

  QualityEvent handle(Command command);

  record Command(QualityEvent event) {}
}
