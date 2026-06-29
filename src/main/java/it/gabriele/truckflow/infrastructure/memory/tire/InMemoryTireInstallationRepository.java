package it.gabriele.truckflow.infrastructure.memory.tire;

import it.gabriele.truckflow.application.port.out.tire.TireInstallationRepository;
import it.gabriele.truckflow.domain.tire.TireInstallation;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per TireInstallation. */
public final class InMemoryTireInstallationRepository extends InMemoryRepository<TireInstallation>
    implements TireInstallationRepository {

  public InMemoryTireInstallationRepository() {
    super(
        item ->
            item.getTire().getTireCode()
                + "@"
                + item.getVehicleFleetNumber()
                + "@"
                + item.getWheelPosition());
  }
}
