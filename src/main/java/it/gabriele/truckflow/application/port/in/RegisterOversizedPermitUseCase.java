package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.oversized.OversizedPermit;

public interface RegisterOversizedPermitUseCase {
  OversizedPermit handle(Command command);

  record Command(OversizedPermit permit) {}
}
