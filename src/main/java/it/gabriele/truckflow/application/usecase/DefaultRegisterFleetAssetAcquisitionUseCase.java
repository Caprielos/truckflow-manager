package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.RegisterFleetAssetAcquisitionUseCase;
import it.gabriele.truckflow.application.port.out.FleetAssetAcquisitionRepository;
import it.gabriele.truckflow.domain.economics.FleetAssetAcquisition;
import java.util.Objects;

/** Caso d'uso: registrare un acquisto complesso di flotta con componenti e IVA. */
public final class DefaultRegisterFleetAssetAcquisitionUseCase
    implements RegisterFleetAssetAcquisitionUseCase {

  private final FleetAssetAcquisitionRepository repository;

  public DefaultRegisterFleetAssetAcquisitionUseCase(FleetAssetAcquisitionRepository repository) {
    this.repository =
        Objects.requireNonNull(repository, "Il repository acquisizioni flotta è obbligatorio.");
  }

  @Override
  public FleetAssetAcquisition handle(Command command) {
    Objects.requireNonNull(command, "Il comando acquisizione flotta è obbligatorio.");
    repository.save(command.acquisition());
    return command.acquisition();
  }
}
