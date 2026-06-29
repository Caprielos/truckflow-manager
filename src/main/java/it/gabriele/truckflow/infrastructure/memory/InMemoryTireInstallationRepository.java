package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.TireInstallationRepository;
import it.gabriele.truckflow.domain.tire.TireInstallation;

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
