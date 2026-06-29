package it.gabriele.truckflow.infrastructure.memory.tracking;

import it.gabriele.truckflow.application.port.out.TrackingTimelineRepository;
import it.gabriele.truckflow.domain.tracking.TrackingTimeline;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per TrackingTimeline. */
public final class InMemoryTrackingTimelineRepository extends InMemoryRepository<TrackingTimeline>
    implements TrackingTimelineRepository {

  public InMemoryTrackingTimelineRepository() {
    super(item -> item.getMissionNumber());
  }
}
