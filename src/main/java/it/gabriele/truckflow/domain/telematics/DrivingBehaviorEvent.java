package it.gabriele.truckflow.domain.telematics;

import it.gabriele.truckflow.domain.location.GeoCoordinates;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Objects;

public final class DrivingBehaviorEvent {

  private final String vehicleFleetNumber;
  private final DrivingBehaviorEventType type;
  private final Instant occurredAt;
  private final GeoCoordinates coordinates;
  private final Notes notes;

  private DrivingBehaviorEvent(
      String vehicleFleetNumber,
      DrivingBehaviorEventType type,
      Instant occurredAt,
      GeoCoordinates coordinates,
      Notes notes) {
    if (vehicleFleetNumber == null || vehicleFleetNumber.trim().isEmpty()) {
      throw new IllegalArgumentException("Il numero flotta mezzo è obbligatorio.");
    }
    if (type == null) {
      throw new IllegalArgumentException("Il tipo evento guida è obbligatorio.");
    }
    if (occurredAt == null) {
      throw new IllegalArgumentException("L'istante evento guida è obbligatorio.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note evento guida sono obbligatorie.");
    }
    this.vehicleFleetNumber = vehicleFleetNumber.trim().toUpperCase();
    this.type = type;
    this.occurredAt = occurredAt;
    this.coordinates = coordinates;
    this.notes = notes;
  }

  public static DrivingBehaviorEvent of(
      String vehicleFleetNumber,
      DrivingBehaviorEventType type,
      Instant occurredAt,
      GeoCoordinates coordinates,
      Notes notes) {
    return new DrivingBehaviorEvent(vehicleFleetNumber, type, occurredAt, coordinates, notes);
  }

  public String getVehicleFleetNumber() {
    return vehicleFleetNumber;
  }

  public DrivingBehaviorEventType getType() {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DrivingBehaviorEvent that)) return false;
    return vehicleFleetNumber.equals(that.vehicleFleetNumber)
        && type == that.type
        && occurredAt.equals(that.occurredAt)
        && Objects.equals(coordinates, that.coordinates)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleFleetNumber, type, occurredAt, coordinates, notes);
  }
}
