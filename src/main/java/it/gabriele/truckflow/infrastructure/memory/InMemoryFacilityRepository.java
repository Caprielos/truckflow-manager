package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.FacilityRepository;
import it.gabriele.truckflow.domain.facility.Facility;

/** Repository in memoria per Facility. */
public final class InMemoryFacilityRepository extends InMemoryRepository<Facility>
    implements FacilityRepository {

  public InMemoryFacilityRepository() {
    super(item -> item.getCode());
  }
}
