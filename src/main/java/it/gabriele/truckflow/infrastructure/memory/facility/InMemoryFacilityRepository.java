package it.gabriele.truckflow.infrastructure.memory.facility;

import it.gabriele.truckflow.application.port.out.FacilityRepository;
import it.gabriele.truckflow.domain.facility.Facility;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per Facility. */
public final class InMemoryFacilityRepository extends InMemoryRepository<Facility>
    implements FacilityRepository {

  public InMemoryFacilityRepository() {
    super(item -> item.getCode());
  }
}
