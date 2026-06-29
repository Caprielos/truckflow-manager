package it.gabriele.truckflow.domain.claim;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/** Checklist danni pre-partenza o post-rientro. */
public final class DamageInspection {

  private final String inspectionNumber;
  private final String vehicleFleetNumber;
  private final String driverCode;
  private final LocalDateTime performedAt;
  private final List<DamageInspectionItem> items;
  private final Notes notes;

  private DamageInspection(
      String inspectionNumber,
      String vehicleFleetNumber,
      String driverCode,
      LocalDateTime performedAt,
      List<DamageInspectionItem> items,
      Notes notes) {
    this.inspectionNumber =
        normalize(inspectionNumber, "Il numero controllo danni è obbligatorio.");
    this.vehicleFleetNumber =
        normalize(vehicleFleetNumber, "Il numero flotta mezzo è obbligatorio.");
    this.driverCode = normalize(driverCode, "Il codice autista è obbligatorio.");
    if (performedAt == null) {
      throw new IllegalArgumentException("La data controllo danni è obbligatoria.");
    }
    if (items == null || items.isEmpty()) {
      throw new IllegalArgumentException("La checklist danni deve avere almeno una voce.");
    }
    if (items.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("La checklist danni non può contenere valori nulli.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note controllo danni sono obbligatorie.");
    }
    this.performedAt = performedAt;
    this.items = List.copyOf(items);
    this.notes = notes;
  }

  public static DamageInspection of(
      String inspectionNumber,
      String vehicleFleetNumber,
      String driverCode,
      LocalDateTime performedAt,
      List<DamageInspectionItem> items,
      Notes notes) {
    return new DamageInspection(
        inspectionNumber, vehicleFleetNumber, driverCode, performedAt, items, notes);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }

  public String getInspectionNumber() {
    return inspectionNumber;
  }

  public String getVehicleFleetNumber() {
    return vehicleFleetNumber;
  }

  public String getDriverCode() {
    return driverCode;
  }

  public LocalDateTime getPerformedAt() {
    return performedAt;
  }

  public List<DamageInspectionItem> getItems() {
    return items;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean hasNewDamage() {
    return items.stream().anyMatch(DamageInspectionItem::isDamaged);
  }
}
