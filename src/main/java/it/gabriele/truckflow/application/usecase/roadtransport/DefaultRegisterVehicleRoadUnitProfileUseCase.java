package it.gabriele.truckflow.application.usecase.roadtransport;

import it.gabriele.truckflow.application.port.in.roadtransport.RegisterVehicleRoadUnitProfileUseCase;
import it.gabriele.truckflow.application.port.out.roadtransport.VehicleRoadUnitProfileRepository;
import it.gabriele.truckflow.domain.roadtransport.VehicleRoadUnitProfile;
import java.util.Objects;

/** Implementazione default di RegisterVehicleRoadUnitProfileUseCase. */
public final class DefaultRegisterVehicleRoadUnitProfileUseCase
    implements RegisterVehicleRoadUnitProfileUseCase {

  private final VehicleRoadUnitProfileRepository repository;

  public DefaultRegisterVehicleRoadUnitProfileUseCase(VehicleRoadUnitProfileRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public VehicleRoadUnitProfile handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    VehicleRoadUnitProfile aggregate =
        Objects.requireNonNull(command.profile(), "Il profilo fisico veicolo è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
