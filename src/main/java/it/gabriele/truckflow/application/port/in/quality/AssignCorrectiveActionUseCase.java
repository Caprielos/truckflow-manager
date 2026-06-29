package it.gabriele.truckflow.application.port.in.quality;

import it.gabriele.truckflow.domain.quality.CorrectiveAction;
import it.gabriele.truckflow.domain.quality.QualityEvent;

public interface AssignCorrectiveActionUseCase {

  QualityEvent handle(Command command);

  record Command(String eventCode, CorrectiveAction correctiveAction) {}
}
