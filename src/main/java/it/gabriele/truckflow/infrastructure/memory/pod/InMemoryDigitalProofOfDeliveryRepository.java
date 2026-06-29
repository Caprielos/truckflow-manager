package it.gabriele.truckflow.infrastructure.memory.pod;

import it.gabriele.truckflow.application.port.out.pod.DigitalProofOfDeliveryRepository;
import it.gabriele.truckflow.domain.pod.DigitalProofOfDelivery;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DigitalProofOfDelivery. */
public final class InMemoryDigitalProofOfDeliveryRepository
    extends InMemoryRepository<DigitalProofOfDelivery> implements DigitalProofOfDeliveryRepository {

  public InMemoryDigitalProofOfDeliveryRepository() {
    super(pod -> pod.podCode());
  }
}
