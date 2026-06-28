package it.gabriele.truckflow.domain.tracking;

import it.gabriele.truckflow.domain.location.GeoCoordinates;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa TrackingEvent.
 */
class TrackingEventTest {

    @Test
    void shouldCreateTrackingEvent() {
        TrackingEvent event = standardEvent();

        assertEquals("TRK-001", event.getEventCode());
        assertEquals("MIS-001", event.getMissionNumber());
        assertEquals("SHP-001", event.getShipmentNumber());
        assertEquals(TrackingEventType.DEPARTED, event.getType());
        assertEquals(Instant.parse("2026-07-01T08:00:00Z"), event.getOccurredAt());
        assertFalse(event.hasCoordinates());
        assertFalse(event.hasNotes());
        assertTrue(event.isOperationalMilestone());
        assertFalse(event.isExceptionEvent());
    }

    @Test
    void shouldCreatePositionRecordedEventWithCoordinates() {
        GeoCoordinates coordinates = GeoCoordinates.of(45.4642, 9.1900);

        TrackingEvent event = TrackingEvent.of(
                "TRK-002",
                "MIS-001",
                "SHP-001",
                TrackingEventType.POSITION_RECORDED,
                Instant.parse("2026-07-01T09:00:00Z"),
                coordinates,
                Notes.empty()
        );

        assertTrue(event.hasCoordinates());
        assertEquals(coordinates, event.getCoordinates());
    }

    @Test
    void shouldNotAllowPositionRecordedWithoutCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> TrackingEvent.of(
                "TRK-002",
                "MIS-001",
                "SHP-001",
                TrackingEventType.POSITION_RECORDED,
                Instant.parse("2026-07-01T09:00:00Z"),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldNormalizeCodes() {
        TrackingEvent event = TrackingEvent.of(
                "  trk_001  ",
                "  mis_001  ",
                "  shp_001  ",
                TrackingEventType.DEPARTED,
                Instant.parse("2026-07-01T08:00:00Z"),
                null,
                Notes.empty()
        );

        assertEquals("TRK_001", event.getEventCode());
        assertEquals("MIS_001", event.getMissionNumber());
        assertEquals("SHP_001", event.getShipmentNumber());
    }

    @Test
    void shouldRejectInvalidCodes() {
        assertThrows(IllegalArgumentException.class, () -> TrackingEvent.of(
                null,
                "MIS-001",
                "SHP-001",
                TrackingEventType.DEPARTED,
                Instant.parse("2026-07-01T08:00:00Z"),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TrackingEvent.of(
                "TRK 001",
                "MIS-001",
                "SHP-001",
                TrackingEventType.DEPARTED,
                Instant.parse("2026-07-01T08:00:00Z"),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> TrackingEvent.of(
                "TRK-001",
                "MIS-001",
                "SHP-001",
                null,
                Instant.parse("2026-07-01T08:00:00Z"),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TrackingEvent.of(
                "TRK-001",
                "MIS-001",
                "SHP-001",
                TrackingEventType.DEPARTED,
                null,
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TrackingEvent.of(
                "TRK-001",
                "MIS-001",
                "SHP-001",
                TrackingEventType.DEPARTED,
                Instant.parse("2026-07-01T08:00:00Z"),
                null,
                null
        ));
    }

    @Test
    void shouldDetectDelayAndIncident() {
        TrackingEvent delay = TrackingEvent.of(
                "TRK-003",
                "MIS-001",
                "SHP-001",
                TrackingEventType.DELAY_REPORTED,
                Instant.parse("2026-07-01T10:00:00Z"),
                null,
                Notes.of("Traffico intenso")
        );

        TrackingEvent incident = TrackingEvent.of(
                "TRK-004",
                "MIS-001",
                "SHP-001",
                TrackingEventType.INCIDENT_REPORTED,
                Instant.parse("2026-07-01T11:00:00Z"),
                GeoCoordinates.of(45.4642, 9.1900),
                Notes.of("Incidente segnalato")
        );

        assertTrue(delay.isDelay());
        assertTrue(delay.isExceptionEvent());
        assertTrue(delay.hasNotes());

        assertTrue(incident.isIncident());
        assertTrue(incident.isExceptionEvent());
        assertTrue(incident.hasCoordinates());
    }

    @Test
    void shouldCheckSameMissionAndShipment() {
        TrackingEvent first = standardEvent();

        TrackingEvent second = TrackingEvent.of(
                "TRK-002",
                "MIS-001",
                "SHP-001",
                TrackingEventType.ARRIVED,
                Instant.parse("2026-07-01T12:00:00Z"),
                null,
                Notes.empty()
        );

        assertTrue(first.isSameMission(second));
        assertTrue(first.isSameShipment(second));
        assertTrue(first.isBeforeOrAtSameTime(second));
    }

    @Test
    void shouldFormatSingleLine() {
        assertEquals(
                "TRK-001 - MIS-001 - SHP-001 - DEPARTED - 2026-07-01T08:00:00Z",
                standardEvent().formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentEventsEqual() {
        TrackingEvent first = standardEvent();
        TrackingEvent second = standardEvent();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    private static TrackingEvent standardEvent() {
        return TrackingEvent.of(
                "TRK-001",
                "MIS-001",
                "SHP-001",
                TrackingEventType.DEPARTED,
                Instant.parse("2026-07-01T08:00:00Z"),
                null,
                Notes.empty()
        );
    }
}
