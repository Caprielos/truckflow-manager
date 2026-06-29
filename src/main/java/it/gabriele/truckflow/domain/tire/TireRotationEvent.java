package it.gabriele.truckflow.domain.tire;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.Objects;

/** Evento storico di rotazione/spostamento gomma. */
public final class TireRotationEvent {

  private final String tireCode;
  private final String vehicleFleetNumber;
  private final WheelPosition fromPosition;
  private final WheelPosition toPosition;
  private final LocalDate occurredAt;
  private final long odometerKilometers;
  private final Notes notes;

  private TireRotationEvent(
      String tireCode,
      String vehicleFleetNumber,
      WheelPosition fromPosition,
      WheelPosition toPosition,
      LocalDate occurredAt,
      long odometerKilometers,
      Notes notes) {
    if (tireCode == null || tireCode.trim().isEmpty()) {
      throw new IllegalArgumentException("Il codice gomma è obbligatorio.");
    }
    if (vehicleFleetNumber == null || vehicleFleetNumber.trim().isEmpty()) {
      throw new IllegalArgumentException("Il numero flotta mezzo è obbligatorio.");
    }
    if (fromPosition == null || toPosition == null) {
      throw new IllegalArgumentException("Le posizioni rotazione sono obbligatorie.");
    }
    if (fromPosition.equals(toPosition)) {
      throw new IllegalArgumentException(
          "La posizione di partenza e arrivo devono essere diverse.");
    }
    if (occurredAt == null) {
      throw new IllegalArgumentException("La data rotazione è obbligatoria.");
    }
    if (odometerKilometers < 0) {
      throw new IllegalArgumentException("I km rotazione non possono essere negativi.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note rotazione sono obbligatorie.");
    }
    this.tireCode = tireCode.trim().toUpperCase();
    this.vehicleFleetNumber = vehicleFleetNumber.trim().toUpperCase();
    this.fromPosition = fromPosition;
    this.toPosition = toPosition;
    this.occurredAt = occurredAt;
    this.odometerKilometers = odometerKilometers;
    this.notes = notes;
  }

  public static TireRotationEvent of(
      String tireCode,
      String vehicleFleetNumber,
      WheelPosition fromPosition,
      WheelPosition toPosition,
      LocalDate occurredAt,
      long odometerKilometers,
      Notes notes) {
    return new TireRotationEvent(
        tireCode,
        vehicleFleetNumber,
        fromPosition,
        toPosition,
        occurredAt,
        odometerKilometers,
        notes);
  }

  public String getTireCode() {
    return tireCode;
  }

  public String getVehicleFleetNumber() {
    return vehicleFleetNumber;
  }

  public WheelPosition getFromPosition() {
    return fromPosition;
  }

  public WheelPosition getToPosition() {
    return toPosition;
  }

  public LocalDate getOccurredAt() {
    return occurredAt;
  }

  public long getOdometerKilometers() {
    return odometerKilometers;
  }

  public Notes getNotes() {
    return notes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TireRotationEvent that)) return false;
    return odometerKilometers == that.odometerKilometers
        && tireCode.equals(that.tireCode)
        && vehicleFleetNumber.equals(that.vehicleFleetNumber)
        && fromPosition.equals(that.fromPosition)
        && toPosition.equals(that.toPosition)
        && occurredAt.equals(that.occurredAt)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        tireCode,
        vehicleFleetNumber,
        fromPosition,
        toPosition,
        occurredAt,
        odometerKilometers,
        notes);
  }
}
