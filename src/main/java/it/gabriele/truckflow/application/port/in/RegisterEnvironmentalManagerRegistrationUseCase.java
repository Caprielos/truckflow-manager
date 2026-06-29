package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.waste.EnvironmentalManagerRegistration;

public interface RegisterEnvironmentalManagerRegistrationUseCase {
  EnvironmentalManagerRegistration handle(Command command);

  record Command(EnvironmentalManagerRegistration registration) {}
}
