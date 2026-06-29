package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.EnvironmentalManagerRegistrationRepository;
import it.gabriele.truckflow.domain.waste.EnvironmentalManagerRegistration;

/** Repository in memoria per EnvironmentalManagerRegistration. */
public final class InMemoryEnvironmentalManagerRegistrationRepository
    extends InMemoryRepository<EnvironmentalManagerRegistration>
    implements EnvironmentalManagerRegistrationRepository {

  public InMemoryEnvironmentalManagerRegistrationRepository() {
    super(registration -> registration.registrationCode());
  }
}
