package it.gabriele.truckflow.domain.fuel;

import it.gabriele.truckflow.domain.shared.Money;
import java.time.LocalDateTime;
import java.util.Objects;

public final class FuelTransaction {

  private final String vehicleFleetNumber;
  private final LocalDateTime occurredAt;
  private final double liters;
  private final Money pricePerLiter;
  private final long odometerKilometers;
  private final FuelCardProvider cardProvider;

  private FuelTransaction(
      String vehicleFleetNumber,
      LocalDateTime occurredAt,
      double liters,
      Money pricePerLiter,
      long odometerKilometers,
      FuelCardProvider cardProvider) {
    if (vehicleFleetNumber == null || vehicleFleetNumber.trim().isEmpty()) {
      throw new IllegalArgumentException("Il numero flotta mezzo è obbligatorio.");
    }
    if (occurredAt == null) {
      throw new IllegalArgumentException("La data rifornimento è obbligatoria.");
    }
    if (liters <= 0 || Double.isNaN(liters) || Double.isInfinite(liters)) {
      throw new IllegalArgumentException("I litri devono essere positivi.");
    }
    if (pricePerLiter == null) {
      throw new IllegalArgumentException("Il prezzo al litro è obbligatorio.");
    }
    if (odometerKilometers < 0) {
      throw new IllegalArgumentException("I chilometri non possono essere negativi.");
    }
    if (cardProvider == null) {
      throw new IllegalArgumentException("Il provider carta carburante è obbligatorio.");
    }
    this.vehicleFleetNumber = vehicleFleetNumber.trim().toUpperCase();
    this.occurredAt = occurredAt;
    this.liters = liters;
    this.pricePerLiter = pricePerLiter;
    this.odometerKilometers = odometerKilometers;
    this.cardProvider = cardProvider;
  }

  public static FuelTransaction of(
      String vehicleFleetNumber,
      LocalDateTime occurredAt,
      double liters,
      Money pricePerLiter,
      long odometerKilometers,
      FuelCardProvider cardProvider) {
    return new FuelTransaction(
        vehicleFleetNumber, occurredAt, liters, pricePerLiter, odometerKilometers, cardProvider);
  }

  public String getVehicleFleetNumber() {
    return vehicleFleetNumber;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }

  public double getLiters() {
    return liters;
  }

  public Money getPricePerLiter() {
    return pricePerLiter;
  }

  public long getOdometerKilometers() {
    return odometerKilometers;
  }

  public FuelCardProvider getCardProvider() {
    return cardProvider;
  }

  public double calculateKilometersPerLiter(FuelTransaction previousTransaction) {
    if (previousTransaction == null) {
      throw new IllegalArgumentException("Il rifornimento precedente è obbligatorio.");
    }
    if (!vehicleFleetNumber.equals(previousTransaction.vehicleFleetNumber)) {
      throw new IllegalArgumentException("I rifornimenti devono riferirsi allo stesso mezzo.");
    }
    long kilometers = odometerKilometers - previousTransaction.odometerKilometers;
    if (kilometers < 0) {
      throw new IllegalArgumentException(
          "I chilometri attuali non possono essere inferiori al rifornimento precedente.");
    }
    return kilometers / liters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FuelTransaction that)) return false;
    return Double.compare(liters, that.liters) == 0
        && odometerKilometers == that.odometerKilometers
        && vehicleFleetNumber.equals(that.vehicleFleetNumber)
        && occurredAt.equals(that.occurredAt)
        && pricePerLiter.equals(that.pricePerLiter)
        && cardProvider == that.cardProvider;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        vehicleFleetNumber, occurredAt, liters, pricePerLiter, odometerKilometers, cardProvider);
  }
}
