package it.gabriele.truckflow.domain.tracking;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/** Timeline ordinata degli eventi tracking di una missione. */
public final class TrackingTimeline {

  private final List<TrackingEvent> events;

  private TrackingTimeline(List<TrackingEvent> events) {
    if (events == null) {
      throw new IllegalArgumentException("La lista eventi tracking è obbligatoria.");
    }

    if (events.isEmpty()) {
      throw new IllegalArgumentException("La timeline deve contenere almeno un evento.");
    }

    if (events.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException(
          "La lista eventi tracking non può contenere valori nulli.");
    }

    validateSameMissionAndShipment(events);
    validateUniqueEventCodes(events);

    this.events =
        events.stream().sorted(Comparator.comparing(TrackingEvent::getOccurredAt)).toList();
  }

  public static TrackingTimeline of(List<TrackingEvent> events) {
    return new TrackingTimeline(events);
  }

  public static TrackingTimeline of(TrackingEvent firstEvent, TrackingEvent... otherEvents) {
    if (firstEvent == null) {
      throw new IllegalArgumentException("Il primo evento tracking è obbligatorio.");
    }

    java.util.ArrayList<TrackingEvent> events = new java.util.ArrayList<>();
    events.add(firstEvent);

    if (otherEvents != null) {
      for (TrackingEvent event : otherEvents) {
        events.add(event);
      }
    }

    return new TrackingTimeline(events);
  }

  private static void validateSameMissionAndShipment(List<TrackingEvent> events) {
    TrackingEvent first = events.get(0);

    boolean allSameMission = events.stream().allMatch(first::isSameMission);

    boolean allSameShipment = events.stream().allMatch(first::isSameShipment);

    if (!allSameMission || !allSameShipment) {
      throw new IllegalArgumentException(
          "Tutti gli eventi tracking devono appartenere alla stessa missione e spedizione.");
    }
  }

  private static void validateUniqueEventCodes(List<TrackingEvent> events) {
    long uniqueCodes = events.stream().map(TrackingEvent::getEventCode).distinct().count();

    if (uniqueCodes != events.size()) {
      throw new IllegalArgumentException("La timeline non può contenere codici evento duplicati.");
    }
  }

  public List<TrackingEvent> getEvents() {
    return events;
  }

  public int getEventCount() {
    return events.size();
  }

  public String getMissionNumber() {
    return events.get(0).getMissionNumber();
  }

  public String getShipmentNumber() {
    return events.get(0).getShipmentNumber();
  }

  public TrackingEvent getFirstEvent() {
    return events.get(0);
  }

  public TrackingEvent getLatestEvent() {
    return events.get(events.size() - 1);
  }

  public boolean containsEventCode(String eventCode) {
    if (eventCode == null || eventCode.trim().isEmpty()) {
      throw new IllegalArgumentException("Il codice evento da cercare è obbligatorio.");
    }

    String normalizedEventCode = eventCode.trim().toUpperCase();

    return events.stream().anyMatch(event -> event.getEventCode().equals(normalizedEventCode));
  }

  public boolean hasEventType(TrackingEventType type) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo evento da cercare è obbligatorio.");
    }

    return events.stream().anyMatch(event -> event.getType() == type);
  }

  public boolean hasDelays() {
    return events.stream().anyMatch(TrackingEvent::isDelay);
  }

  public boolean hasIncidents() {
    return events.stream().anyMatch(TrackingEvent::isIncident);
  }

  public boolean hasPickupCompleted() {
    return hasEventType(TrackingEventType.PICKUP_COMPLETED);
  }

  public boolean hasDeliveryCompleted() {
    return hasEventType(TrackingEventType.DELIVERY_COMPLETED);
  }

  public boolean hasMissionCompleted() {
    return hasEventType(TrackingEventType.MISSION_COMPLETED);
  }

  public List<TrackingEvent> getEventsByType(TrackingEventType type) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo evento da filtrare è obbligatorio.");
    }

    return events.stream().filter(event -> event.getType() == type).toList();
  }

  public String formatSingleLine() {
    return "mission: "
        + getMissionNumber()
        + " - shipment: "
        + getShipmentNumber()
        + " - events: "
        + events.size()
        + " - latest: "
        + getLatestEvent().getType();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TrackingTimeline that)) return false;
    return events.equals(that.events);
  }

  @Override
  public int hashCode() {
    return Objects.hash(events);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
