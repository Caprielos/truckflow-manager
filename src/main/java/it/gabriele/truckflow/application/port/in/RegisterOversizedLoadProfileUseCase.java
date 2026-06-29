package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.oversized.OversizedLoadProfile;

public interface RegisterOversizedLoadProfileUseCase {
  OversizedLoadProfile handle(Command command);

  record Command(OversizedLoadProfile load) {}
}
