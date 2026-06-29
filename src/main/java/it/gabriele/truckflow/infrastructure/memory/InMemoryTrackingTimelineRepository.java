package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.TrackingTimelineRepository;
import it.gabriele.truckflow.domain.tracking.TrackingTimeline;

/** Repository in memoria per TrackingTimeline. */
public final class InMemoryTrackingTimelineRepository extends InMemoryRepository<TrackingTimeline> implements TrackingTimelineRepository {

    public InMemoryTrackingTimelineRepository() {
        super(item -> item.getMissionNumber());
    }
}
