package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.TachographActivityRepository;
import it.gabriele.truckflow.domain.tachograph.TachographActivity;

/** Repository in memoria per TachographActivity. */
public final class InMemoryTachographActivityRepository
    extends InMemoryRepository<TachographActivity> implements TachographActivityRepository {

  public InMemoryTachographActivityRepository() {
    super(
        activity -> activity.driverCode() + "_" + activity.startAt().toString().replace(":", "-"));
  }
}
