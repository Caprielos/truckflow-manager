package it.gabriele.truckflow.domain.tracking;

import it.gabriele.truckflow.domain.location.GeoCoordinates;
import it.gabriele.truckflow.domain.operation.TransportMission;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Objects;

/** Evento di tracking collegato a una missione e a una spedizione. */
public final class TrackingEvent {

  private static final int MAX_CODE_LENGTH = 50;

  private final String eventCode;
  private final String missionNumber;
  private final String shipmentNumber;
  private final TrackingEventType type;
  private final Instant occurredAt;
  private final GeoCoordinates coordinates;
  private final Notes notes;

  private TrackingEvent(
      String eventCode,
      String missionNumber,
      String shipmentNumber,
      TrackingEventType type,
      Instant occurredAt,
      GeoCoordinates coordinates,
      Notes notes) {
    this.eventCode = validateCode(eventCode, "Il codice evento è obbligatorio.");
    this.missionNumber = validateCode(missionNumber, "Il numero missione è obbligatorio.");
    this.shipmentNumber = validateCode(shipmentNumber, "Il numero spedizione è obbligatorio.");

    if (type == null) {
      throw new IllegalArgumentException("Il tipo evento tracking è obbligatorio.");
    }

    if (occurredAt == null) {
      throw new IllegalArgumentException("La data e ora dell'evento tracking sono obbligatorie.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note dell'evento tracking sono obbligatorie.");
    }

    if (type.requiresCoordinates() && coordinates == null) {
      throw new IllegalArgumentException("Questo tipo evento richiede le coordinate.");
    }

    this.type = type;
    this.occurredAt = occurredAt;
    this.coordinates = coordinates;
    this.notes = notes;
  }

  public static TrackingEvent of(
      String eventCode,
      String missionNumber,
      String shipmentNumber,
      TrackingEventType type,
      Instant occurredAt,
      GeoCoordinates coordinates,
      Notes notes) {
    return new TrackingEvent(
        eventCode, missionNumber, shipmentNumber, type, occurredAt, coordinates, notes);
  }

  public static TrackingEvent fromMission(
      String eventCode,
      TransportMission mission,
      TrackingEventType type,
      Instant occurredAt,
      GeoCoordinates coordinates,
      Notes notes) {
    if (mission == null) {
      throw new IllegalArgumentException("La missione è obbligatoria.");
    }

    return new TrackingEvent(
        eventCode,
        mission.getMissionNumber(),
        mission.getShipment().getShipmentNumber(),
        type,
        occurredAt,
        coordinates,
        notes);
  }

  public static TrackingEvent positionRecorded(
      String eventCode,
      TransportMission mission,
      Instant occurredAt,
      GeoCoordinates coordinates,
      Notes notes) {
    return fromMission(
        eventCode, mission, TrackingEventType.POSITION_RECORDED, occurredAt, coordinates, notes);
  }

  public static TrackingEvent delayReported(
      String eventCode, TransportMission mission, Instant occurredAt, Notes notes) {
    return fromMission(
        eventCode, mission, TrackingEventType.DELAY_REPORTED, occurredAt, null, notes);
  }

  public static TrackingEvent incidentReported(
      String eventCode,
      TransportMission mission,
      Instant occurredAt,
      GeoCoordinates coordinates,
      Notes notes) {
    return fromMission(
        eventCode, mission, TrackingEventType.INCIDENT_REPORTED, occurredAt, coordinates, notes);
  }

  private static String validateCode(String code, String nullMessage) {
    if (code == null) {
      throw new IllegalArgumentException(nullMessage);
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  public String getEventCode() {
    return eventCode;
  }

  public String getMissionNumber() {
    return missionNumber;
  }

  public String getShipmentNumber() {
    return shipmentNumber;
  }

  public TrackingEventType getType() {
    return type;
  }

  public Instant getOccurredAt() {
    return occurredAt;
  }

  public GeoCoordinates getCoordinates() {
    return coordinates;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean hasCoordinates() {
    return coordinates != null;
  }

  public boolean hasNotes() {
    return notes.hasText();
  }

  public boolean isOperationalMilestone() {
    return type.isOperationalMilestone();
  }

  public boolean isExceptionEvent() {
    return type.isExceptionEvent();
  }

  public boolean isDelay() {
    return type == TrackingEventType.DELAY_REPORTED;
  }

  public boolean isIncident() {
    return type == TrackingEventType.INCIDENT_REPORTED;
  }

  public boolean isSameMission(TrackingEvent other) {
    if (other == null) {
      throw new IllegalArgumentException("L'evento da confrontare è obbligatorio.");
    }

    return missionNumber.equals(other.missionNumber);
  }

  public boolean isSameShipment(TrackingEvent other) {
    if (other == null) {
      throw new IllegalArgumentException("L'evento da confrontare è obbligatorio.");
    }

    return shipmentNumber.equals(other.shipmentNumber);
  }

  public boolean isBeforeOrAtSameTime(TrackingEvent other) {
    if (other == null) {
      throw new IllegalArgumentException("L'evento da confrontare è obbligatorio.");
    }

    return !occurredAt.isAfter(other.occurredAt);
  }

  public String formatSingleLine() {
    return eventCode
        + " - "
        + missionNumber
        + " - "
        + shipmentNumber
        + " - "
        + type
        + " - "
        + occurredAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TrackingEvent that)) return false;
    return eventCode.equals(that.eventCode)
        && missionNumber.equals(that.missionNumber)
        && shipmentNumber.equals(that.shipmentNumber)
        && type == that.type
        && occurredAt.equals(that.occurredAt)
        && Objects.equals(coordinates, that.coordinates)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        eventCode, missionNumber, shipmentNumber, type, occurredAt, coordinates, notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
