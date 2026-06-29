package it.gabriele.truckflow.domain.tire;

import java.time.LocalDate;
import java.util.Objects;

/** Montaggio reale di una gomma su un mezzo e una posizione ruota. */
public final class TireInstallation {

  private final Tire tire;
  private final String vehicleFleetNumber;
  private final WheelPosition wheelPosition;
  private final LocalDate installedAt;
  private final long installedAtKilometers;
  private final LocalDate removedAt;
  private final long removedAtKilometers;

  private TireInstallation(
      Tire tire,
      String vehicleFleetNumber,
      WheelPosition wheelPosition,
      LocalDate installedAt,
      long installedAtKilometers,
      LocalDate removedAt,
      long removedAtKilometers) {
    if (tire == null) {
      throw new IllegalArgumentException("La gomma è obbligatoria.");
    }
    if (vehicleFleetNumber == null || vehicleFleetNumber.trim().isEmpty()) {
      throw new IllegalArgumentException("Il numero flotta mezzo è obbligatorio.");
    }
    if (wheelPosition == null) {
      throw new IllegalArgumentException("La posizione ruota è obbligatoria.");
    }
    if (installedAt == null) {
      throw new IllegalArgumentException("La data installazione è obbligatoria.");
    }
    if (installedAtKilometers < 0) {
      throw new IllegalArgumentException("I km installazione non possono essere negativi.");
    }
    if (removedAt != null && removedAt.isBefore(installedAt)) {
      throw new IllegalArgumentException("La data rimozione non può precedere l'installazione.");
    }
    if (removedAtKilometers < 0) {
      throw new IllegalArgumentException("I km rimozione non possono essere negativi.");
    }
    if (removedAt != null && removedAtKilometers < installedAtKilometers) {
      throw new IllegalArgumentException(
          "I km rimozione non possono essere inferiori ai km installazione.");
    }
    this.tire = tire;
    this.vehicleFleetNumber = vehicleFleetNumber.trim().toUpperCase();
    this.wheelPosition = wheelPosition;
    this.installedAt = installedAt;
    this.installedAtKilometers = installedAtKilometers;
    this.removedAt = removedAt;
    this.removedAtKilometers = removedAtKilometers;
  }

  public static TireInstallation active(
      Tire tire,
      String vehicleFleetNumber,
      WheelPosition wheelPosition,
      LocalDate installedAt,
      long installedAtKilometers) {
    return new TireInstallation(
        tire, vehicleFleetNumber, wheelPosition, installedAt, installedAtKilometers, null, 0);
  }

  public TireInstallation remove(LocalDate removedAt, long removedAtKilometers) {
    return new TireInstallation(
        tire,
        vehicleFleetNumber,
        wheelPosition,
        installedAt,
        installedAtKilometers,
        removedAt,
        removedAtKilometers);
  }

  public Tire getTire() {
    return tire;
  }

  public String getVehicleFleetNumber() {
    return vehicleFleetNumber;
  }

  public WheelPosition getWheelPosition() {
    return wheelPosition;
  }

  public LocalDate getInstalledAt() {
    return installedAt;
  }

  public long getInstalledAtKilometers() {
    return installedAtKilometers;
  }

  public LocalDate getRemovedAt() {
    return removedAt;
  }

  public long getRemovedAtKilometers() {
    return removedAtKilometers;
  }

  public boolean isActive() {
    return removedAt == null;
  }

  public long calculateKilometersMounted(long currentVehicleKilometers) {
    if (currentVehicleKilometers < installedAtKilometers) {
      throw new IllegalArgumentException(
          "I km attuali non possono essere inferiori ai km installazione.");
    }
    if (removedAt != null) {
      return removedAtKilometers - installedAtKilometers;
    }
    return currentVehicleKilometers - installedAtKilometers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TireInstallation that)) return false;
    return installedAtKilometers == that.installedAtKilometers
        && removedAtKilometers == that.removedAtKilometers
        && tire.equals(that.tire)
        && vehicleFleetNumber.equals(that.vehicleFleetNumber)
        && wheelPosition.equals(that.wheelPosition)
        && installedAt.equals(that.installedAt)
        && Objects.equals(removedAt, that.removedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        tire,
        vehicleFleetNumber,
        wheelPosition,
        installedAt,
        installedAtKilometers,
        removedAt,
        removedAtKilometers);
  }
}
