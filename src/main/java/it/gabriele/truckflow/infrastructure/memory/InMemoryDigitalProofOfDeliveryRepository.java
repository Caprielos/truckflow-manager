package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DigitalProofOfDeliveryRepository;
import it.gabriele.truckflow.domain.pod.DigitalProofOfDelivery;

/** Repository in memoria per DigitalProofOfDelivery. */
public final class InMemoryDigitalProofOfDeliveryRepository
    extends InMemoryRepository<DigitalProofOfDelivery> implements DigitalProofOfDeliveryRepository {

  public InMemoryDigitalProofOfDeliveryRepository() {
    super(pod -> pod.podCode());
  }
}
