package it.gabriele.truckflow.application.port.in.pod;

import it.gabriele.truckflow.domain.pod.DigitalProofOfDelivery;

public interface CollectDigitalProofOfDeliveryUseCase {
  DigitalProofOfDelivery handle(Command command);

  record Command(DigitalProofOfDelivery pod) {}
}
