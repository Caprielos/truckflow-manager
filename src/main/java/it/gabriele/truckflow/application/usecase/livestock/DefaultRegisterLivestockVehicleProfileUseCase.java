package it.gabriele.truckflow.application.usecase.livestock;

import it.gabriele.truckflow.application.port.in.RegisterLivestockVehicleProfileUseCase;
import it.gabriele.truckflow.application.port.out.LivestockVehicleProfileRepository;
import it.gabriele.truckflow.domain.livestock.LivestockVehicleProfile;
import java.util.Objects;

/** Implementazione default di RegisterLivestockVehicleProfileUseCase. */
public final class DefaultRegisterLivestockVehicleProfileUseCase
    implements RegisterLivestockVehicleProfileUseCase {

  private final LivestockVehicleProfileRepository repository;

  public DefaultRegisterLivestockVehicleProfileUseCase(
      LivestockVehicleProfileRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public LivestockVehicleProfile handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    LivestockVehicleProfile aggregate =
        Objects.requireNonNull(command.profile(), "Il profilo mezzo animali è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
