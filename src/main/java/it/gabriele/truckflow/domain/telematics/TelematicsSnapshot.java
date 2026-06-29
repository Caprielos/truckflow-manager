package it.gabriele.truckflow.domain.telematics;

import java.time.Instant;
import java.util.Objects;

public final class TelematicsSnapshot {

  private final String vehicleFleetNumber;
  private final Instant recordedAt;
  private final double latitude;
  private final double longitude;
  private final Long odometerKilometers;
  private final Double fuelLevelPercentage;

  private TelematicsSnapshot(
      String vehicleFleetNumber,
      Instant recordedAt,
      double latitude,
      double longitude,
      Long odometerKilometers,
      Double fuelLevelPercentage) {
    if (vehicleFleetNumber == null || vehicleFleetNumber.trim().isEmpty()) {
      throw new IllegalArgumentException("Il numero flotta mezzo è obbligatorio.");
    }
    if (recordedAt == null) {
      throw new IllegalArgumentException("La data rilevazione è obbligatoria.");
    }
    if (latitude < -90 || latitude > 90 || Double.isNaN(latitude)) {
      throw new IllegalArgumentException("La latitudine non è valida.");
    }
    if (longitude < -180 || longitude > 180 || Double.isNaN(longitude)) {
      throw new IllegalArgumentException("La longitudine non è valida.");
    }
    if (odometerKilometers != null && odometerKilometers < 0) {
      throw new IllegalArgumentException("I chilometri non possono essere negativi.");
    }
    if (fuelLevelPercentage != null && (fuelLevelPercentage < 0 || fuelLevelPercentage > 100)) {
      throw new IllegalArgumentException("Il livello carburante deve essere tra 0 e 100.");
    }
    this.vehicleFleetNumber = vehicleFleetNumber.trim().toUpperCase();
    this.recordedAt = recordedAt;
    this.latitude = latitude;
    this.longitude = longitude;
    this.odometerKilometers = odometerKilometers;
    this.fuelLevelPercentage = fuelLevelPercentage;
  }

  public static TelematicsSnapshot of(
      String vehicleFleetNumber,
      Instant recordedAt,
      double latitude,
      double longitude,
      Long odometerKilometers,
      Double fuelLevelPercentage) {
    return new TelematicsSnapshot(
        vehicleFleetNumber,
        recordedAt,
        latitude,
        longitude,
        odometerKilometers,
        fuelLevelPercentage);
  }

  public String getVehicleFleetNumber() {
    return vehicleFleetNumber;
  }

  public Instant getRecordedAt() {
    return recordedAt;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public Long getOdometerKilometers() {
    return odometerKilometers;
  }

  public Double getFuelLevelPercentage() {
    return fuelLevelPercentage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TelematicsSnapshot that)) return false;
    return Double.compare(latitude, that.latitude) == 0
        && Double.compare(longitude, that.longitude) == 0
        && vehicleFleetNumber.equals(that.vehicleFleetNumber)
        && recordedAt.equals(that.recordedAt)
        && Objects.equals(odometerKilometers, that.odometerKilometers)
        && Objects.equals(fuelLevelPercentage, that.fuelLevelPercentage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        vehicleFleetNumber,
        recordedAt,
        latitude,
        longitude,
        odometerKilometers,
        fuelLevelPercentage);
  }
}
