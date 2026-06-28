package it.gabriele.truckflow.domain.tracking;

import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa TrackingTimeline.
 */
class TrackingTimelineTest {

    @Test
    void shouldCreateTimelineAndOrderEventsByTime() {
        TrackingEvent second = event("TRK-002", TrackingEventType.ARRIVED, "2026-07-01T12:00:00Z");
        TrackingEvent first = event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z");

        TrackingTimeline timeline = TrackingTimeline.of(List.of(second, first));

        assertEquals(2, timeline.getEventCount());
        assertEquals("TRK-001", timeline.getFirstEvent().getEventCode());
        assertEquals("TRK-002", timeline.getLatestEvent().getEventCode());
        assertEquals("MIS-001", timeline.getMissionNumber());
        assertEquals("SHP-001", timeline.getShipmentNumber());
    }

    @Test
    void shouldNotAllowEmptyNullOrNullItems() {
        assertThrows(IllegalArgumentException.class, () -> TrackingTimeline.of((List<TrackingEvent>) null));
        assertThrows(IllegalArgumentException.class, () -> TrackingTimeline.of(List.of()));

        List<TrackingEvent> eventsWithNull = Arrays.asList(
                event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z"),
                null
        );

        assertThrows(IllegalArgumentException.class, () -> TrackingTimeline.of(eventsWithNull));
    }

    @Test
    void shouldRejectEventsFromDifferentMissionOrShipment() {
        TrackingEvent first = event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z");

        TrackingEvent differentMission = TrackingEvent.of(
                "TRK-002",
                "MIS-002",
                "SHP-001",
                TrackingEventType.ARRIVED,
                Instant.parse("2026-07-01T12:00:00Z"),
                null,
                Notes.empty()
        );

        TrackingEvent differentShipment = TrackingEvent.of(
                "TRK-003",
                "MIS-001",
                "SHP-002",
                TrackingEventType.ARRIVED,
                Instant.parse("2026-07-01T12:00:00Z"),
                null,
                Notes.empty()
        );

        assertThrows(IllegalArgumentException.class, () -> TrackingTimeline.of(first, differentMission));
        assertThrows(IllegalArgumentException.class, () -> TrackingTimeline.of(first, differentShipment));
    }

    @Test
    void shouldRejectDuplicatedEventCodes() {
        TrackingEvent first = event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z");
        TrackingEvent duplicated = event("TRK-001", TrackingEventType.ARRIVED, "2026-07-01T12:00:00Z");

        assertThrows(IllegalArgumentException.class, () -> TrackingTimeline.of(first, duplicated));
    }

    @Test
    void shouldDetectEventTypes() {
        TrackingTimeline timeline = standardTimeline();

        assertTrue(timeline.hasEventType(TrackingEventType.DEPARTED));
        assertTrue(timeline.hasPickupCompleted());
        assertTrue(timeline.hasDeliveryCompleted());
        assertFalse(timeline.hasMissionCompleted());
        assertFalse(timeline.hasDelays());
        assertFalse(timeline.hasIncidents());
    }

    @Test
    void shouldFilterEventsByType() {
        TrackingTimeline timeline = TrackingTimeline.of(
                event("TRK-001", TrackingEventType.POSITION_RECORDED, "2026-07-01T08:00:00Z"),
                event("TRK-002", TrackingEventType.POSITION_RECORDED, "2026-07-01T09:00:00Z"),
                event("TRK-003", TrackingEventType.ARRIVED, "2026-07-01T12:00:00Z")
        );

        assertEquals(2, timeline.getEventsByType(TrackingEventType.POSITION_RECORDED).size());
    }

    @Test
    void shouldDetectDelayAndIncident() {
        TrackingTimeline timeline = TrackingTimeline.of(
                event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z"),
                event("TRK-002", TrackingEventType.DELAY_REPORTED, "2026-07-01T09:00:00Z"),
                event("TRK-003", TrackingEventType.INCIDENT_REPORTED, "2026-07-01T10:00:00Z")
        );

        assertTrue(timeline.hasDelays());
        assertTrue(timeline.hasIncidents());
    }

    @Test
    void shouldCheckContainsEventCode() {
        TrackingTimeline timeline = standardTimeline();

        assertTrue(timeline.containsEventCode("trk-001"));
        assertFalse(timeline.containsEventCode("TRK-999"));
        assertThrows(IllegalArgumentException.class, () -> timeline.containsEventCode(null));
    }

    @Test
    void shouldNotAllowNullTypeFiltering() {
        TrackingTimeline timeline = standardTimeline();

        assertThrows(IllegalArgumentException.class, () -> timeline.hasEventType(null));
        assertThrows(IllegalArgumentException.class, () -> timeline.getEventsByType(null));
    }

    @Test
    void shouldFormatSingleLine() {
        assertEquals(
                "mission: MIS-001 - shipment: SHP-001 - events: 4 - latest: DELIVERY_COMPLETED",
                standardTimeline().formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentTimelinesEqual() {
        TrackingTimeline first = standardTimeline();
        TrackingTimeline second = standardTimeline();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    private static TrackingTimeline standardTimeline() {
        return TrackingTimeline.of(
                event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z"),
                event("TRK-002", TrackingEventType.PICKUP_COMPLETED, "2026-07-01T09:00:00Z"),
                event("TRK-003", TrackingEventType.ARRIVED, "2026-07-01T12:00:00Z"),
                event("TRK-004", TrackingEventType.DELIVERY_COMPLETED, "2026-07-01T13:00:00Z")
        );
    }

    private static TrackingEvent event(String code, TrackingEventType type, String occurredAt) {
        return TrackingEvent.of(
                code,
                "MIS-001",
                "SHP-001",
                type,
                Instant.parse(occurredAt),
                type.requiresCoordinates() ? it.gabriele.truckflow.domain.location.GeoCoordinates.of(45.4642, 9.1900) : null,
                Notes.empty()
        );
    }
}
