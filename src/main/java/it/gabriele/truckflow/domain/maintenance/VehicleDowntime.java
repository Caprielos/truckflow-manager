package it.gabriele.truckflow.domain.maintenance;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDateTime;
import java.util.Objects;

/** Fermo macchina causato da manutenzione o guasto. */
public final class VehicleDowntime {

  private final String vehicleFleetNumber;
  private final LocalDateTime startedAt;
  private final LocalDateTime endedAt;
  private final Money partsCost;
  private final Money laborCost;
  private final Notes notes;

  private VehicleDowntime(
      String vehicleFleetNumber,
      LocalDateTime startedAt,
      LocalDateTime endedAt,
      Money partsCost,
      Money laborCost,
      Notes notes) {
    if (vehicleFleetNumber == null || vehicleFleetNumber.trim().isEmpty()) {
      throw new IllegalArgumentException("Il numero flotta mezzo è obbligatorio.");
    }
    if (startedAt == null) {
      throw new IllegalArgumentException("La data inizio fermo è obbligatoria.");
    }
    if (endedAt != null && endedAt.isBefore(startedAt)) {
      throw new IllegalArgumentException("La data fine fermo non può precedere l'inizio.");
    }
    if (partsCost == null) {
      throw new IllegalArgumentException("Il costo ricambi è obbligatorio.");
    }
    if (laborCost == null) {
      throw new IllegalArgumentException("Il costo manodopera è obbligatorio.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note fermo macchina sono obbligatorie.");
    }
    this.vehicleFleetNumber = vehicleFleetNumber.trim().toUpperCase();
    this.startedAt = startedAt;
    this.endedAt = endedAt;
    this.partsCost = partsCost;
    this.laborCost = laborCost;
    this.notes = notes;
  }

  public static VehicleDowntime of(
      String vehicleFleetNumber,
      LocalDateTime startedAt,
      LocalDateTime endedAt,
      Money partsCost,
      Money laborCost,
      Notes notes) {
    return new VehicleDowntime(vehicleFleetNumber, startedAt, endedAt, partsCost, laborCost, notes);
  }

  public String getVehicleFleetNumber() {
    return vehicleFleetNumber;
  }

  public LocalDateTime getStartedAt() {
    return startedAt;
  }

  public LocalDateTime getEndedAt() {
    return endedAt;
  }

  public Money getPartsCost() {
    return partsCost;
  }

  public Money getLaborCost() {
    return laborCost;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isOpen() {
    return endedAt == null;
  }

  public Money calculateTotalCost() {
    return partsCost.add(laborCost);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof VehicleDowntime that)) return false;
    return vehicleFleetNumber.equals(that.vehicleFleetNumber)
        && startedAt.equals(that.startedAt)
        && Objects.equals(endedAt, that.endedAt)
        && partsCost.equals(that.partsCost)
        && laborCost.equals(that.laborCost)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleFleetNumber, startedAt, endedAt, partsCost, laborCost, notes);
  }
}
