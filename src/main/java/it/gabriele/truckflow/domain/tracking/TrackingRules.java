package it.gabriele.truckflow.domain.tracking;

/**
 * Regole di dominio per il tracking operativo.
 */
public final class TrackingRules {

    private TrackingRules() {
    }

    public static boolean canAddEvent(
            TrackingTimeline timeline,
            TrackingEvent newEvent
    ) {
        validateTimeline(timeline);
        validateEvent(newEvent);

        return timeline.getLatestEvent().isSameMission(newEvent)
                && timeline.getLatestEvent().isSameShipment(newEvent)
                && timeline.getLatestEvent().isBeforeOrAtSameTime(newEvent)
                && !timeline.containsEventCode(newEvent.getEventCode())
                && !timeline.hasMissionCompleted();
    }

    public static boolean requiresOperationalReview(TrackingTimeline timeline) {
        validateTimeline(timeline);

        return timeline.hasDelays() || timeline.hasIncidents();
    }

    public static boolean isPickupAndDeliveryCompleted(TrackingTimeline timeline) {
        validateTimeline(timeline);

        return timeline.hasPickupCompleted()
                && timeline.hasDeliveryCompleted();
    }

    public static boolean isMissionTrackingCompleted(TrackingTimeline timeline) {
        validateTimeline(timeline);

        return timeline.hasMissionCompleted();
    }

    public static boolean hasExceptionEvents(TrackingTimeline timeline) {
        validateTimeline(timeline);

        return timeline.getEvents().stream()
                .anyMatch(TrackingEvent::isExceptionEvent);
    }

    private static void validateTimeline(TrackingTimeline timeline) {
        if (timeline == null) {
            throw new IllegalArgumentException("La timeline tracking è obbligatoria.");
        }
    }

    private static void validateEvent(TrackingEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("L'evento tracking è obbligatorio.");
        }
    }
}
