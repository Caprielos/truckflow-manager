package it.gabriele.truckflow.application.usecase.pod;

import it.gabriele.truckflow.application.port.in.pod.CollectDigitalProofOfDeliveryUseCase;
import it.gabriele.truckflow.application.port.out.pod.DigitalProofOfDeliveryRepository;
import it.gabriele.truckflow.domain.pod.DigitalProofOfDelivery;
import java.util.Objects;

/** Implementazione default di CollectDigitalProofOfDeliveryUseCase. */
public final class DefaultCollectDigitalProofOfDeliveryUseCase
    implements CollectDigitalProofOfDeliveryUseCase {

  private final DigitalProofOfDeliveryRepository repository;

  public DefaultCollectDigitalProofOfDeliveryUseCase(DigitalProofOfDeliveryRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public DigitalProofOfDelivery handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    DigitalProofOfDelivery aggregate =
        Objects.requireNonNull(command.pod(), "Il POD digitale è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
