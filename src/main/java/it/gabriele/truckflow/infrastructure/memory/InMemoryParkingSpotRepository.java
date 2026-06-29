package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.ParkingSpotRepository;
import it.gabriele.truckflow.domain.parking.ParkingSpot;
import java.util.Optional;

/** Repository in memoria per ParkingSpot. */
public final class InMemoryParkingSpotRepository extends InMemoryRepository<ParkingSpot>
    implements ParkingSpotRepository {

  public InMemoryParkingSpotRepository() {
    super(item -> item.getFacilityCode() + ":" + item.getSpotNumber());
  }

  @Override
  public Optional<ParkingSpot> findById(String id) {
    Optional<ParkingSpot> direct = super.findById(id);
    if (direct.isPresent()) {
      return direct;
    }
    String normalized = normalizeId(id);
    return findAll().stream().filter(spot -> spot.getSpotNumber().equals(normalized)).findFirst();
  }
}
