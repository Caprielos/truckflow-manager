package it.gabriele.truckflow.domain.tracking;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import org.junit.jupiter.api.Test;

/** Testa TrackingRules. */
class TrackingRulesTest {

  @Test
  void shouldAllowAddingChronologicalEventForSameMissionAndShipment() {
    TrackingTimeline timeline =
        TrackingTimeline.of(event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z"));

    TrackingEvent nextEvent = event("TRK-002", TrackingEventType.ARRIVED, "2026-07-01T12:00:00Z");

    assertTrue(TrackingRules.canAddEvent(timeline, nextEvent));
  }

  @Test
  void shouldRejectAddingEventBeforeLatestEvent() {
    TrackingTimeline timeline =
        TrackingTimeline.of(
            event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z"),
            event("TRK-002", TrackingEventType.ARRIVED, "2026-07-01T12:00:00Z"));

    TrackingEvent oldEvent =
        event("TRK-003", TrackingEventType.POSITION_RECORDED, "2026-07-01T09:00:00Z");

    assertFalse(TrackingRules.canAddEvent(timeline, oldEvent));
  }

  @Test
  void shouldRejectDuplicatedEventCode() {
    TrackingTimeline timeline =
        TrackingTimeline.of(event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z"));

    TrackingEvent duplicated = event("TRK-001", TrackingEventType.ARRIVED, "2026-07-01T12:00:00Z");

    assertFalse(TrackingRules.canAddEvent(timeline, duplicated));
  }

  @Test
  void shouldRejectEventsAfterMissionCompleted() {
    TrackingTimeline timeline =
        TrackingTimeline.of(
            event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z"),
            event("TRK-002", TrackingEventType.MISSION_COMPLETED, "2026-07-01T18:00:00Z"));

    TrackingEvent nextEvent = event("TRK-003", TrackingEventType.ARRIVED, "2026-07-01T19:00:00Z");

    assertFalse(TrackingRules.canAddEvent(timeline, nextEvent));
  }

  @Test
  void shouldRequireOperationalReviewWhenDelayOrIncidentExists() {
    TrackingTimeline normalTimeline =
        TrackingTimeline.of(event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z"));

    TrackingTimeline delayTimeline =
        TrackingTimeline.of(
            event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z"),
            event("TRK-002", TrackingEventType.DELAY_REPORTED, "2026-07-01T09:00:00Z"));

    assertFalse(TrackingRules.requiresOperationalReview(normalTimeline));
    assertTrue(TrackingRules.requiresOperationalReview(delayTimeline));
    assertTrue(TrackingRules.hasExceptionEvents(delayTimeline));
  }

  @Test
  void shouldDetectPickupAndDeliveryCompleted() {
    TrackingTimeline timeline =
        TrackingTimeline.of(
            event("TRK-001", TrackingEventType.PICKUP_COMPLETED, "2026-07-01T08:00:00Z"),
            event("TRK-002", TrackingEventType.DELIVERY_COMPLETED, "2026-07-01T12:00:00Z"));

    assertTrue(TrackingRules.isPickupAndDeliveryCompleted(timeline));
  }

  @Test
  void shouldDetectMissionTrackingCompleted() {
    TrackingTimeline timeline =
        TrackingTimeline.of(
            event("TRK-001", TrackingEventType.MISSION_COMPLETED, "2026-07-01T18:00:00Z"));

    assertTrue(TrackingRules.isMissionTrackingCompleted(timeline));
  }

  @Test
  void shouldNotAllowNullValues() {
    TrackingTimeline timeline =
        TrackingTimeline.of(event("TRK-001", TrackingEventType.DEPARTED, "2026-07-01T08:00:00Z"));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            TrackingRules.canAddEvent(
                null, event("TRK-002", TrackingEventType.ARRIVED, "2026-07-01T12:00:00Z")));
    assertThrows(IllegalArgumentException.class, () -> TrackingRules.canAddEvent(timeline, null));
    assertThrows(
        IllegalArgumentException.class, () -> TrackingRules.requiresOperationalReview(null));
    assertThrows(
        IllegalArgumentException.class, () -> TrackingRules.isPickupAndDeliveryCompleted(null));
    assertThrows(
        IllegalArgumentException.class, () -> TrackingRules.isMissionTrackingCompleted(null));
    assertThrows(IllegalArgumentException.class, () -> TrackingRules.hasExceptionEvents(null));
  }

  private static TrackingEvent event(String code, TrackingEventType type, String occurredAt) {
    return TrackingEvent.of(
        code,
        "MIS-001",
        "SHP-001",
        type,
        Instant.parse(occurredAt),
        type.requiresCoordinates()
            ? it.gabriele.truckflow.domain.location.GeoCoordinates.of(45.4642, 9.1900)
            : null,
        Notes.empty());
  }
}
