package it.gabriele.truckflow.domain.warehouse;

import java.util.Objects;
import java.util.Set;

/** Ubicazione fisica di magazzino: area, corsia, scaffale, livello, cella. */
public record WarehouseLocation(
    String locationCode,
    WarehouseAreaType areaType,
    String aisle,
    String rack,
    String level,
    int maxPalletPositions,
    boolean temperatureControlled,
    boolean lockedForCompliance,
    Set<String> compatibleCargoTags) {

  public WarehouseLocation {
    locationCode = normalize(locationCode, "Il codice ubicazione è obbligatorio.");
    Objects.requireNonNull(areaType, "Il tipo area è obbligatorio.");
    aisle = normalizeOptional(aisle);
    rack = normalizeOptional(rack);
    level = normalizeOptional(level);
    if (maxPalletPositions <= 0) {
      throw new IllegalArgumentException("Le posizioni pallet devono essere positive.");
    }
    compatibleCargoTags = compatibleCargoTags == null ? Set.of() : Set.copyOf(compatibleCargoTags);
  }

  public boolean canStore(String cargoTag) {
    if (lockedForCompliance) {
      return false;
    }
    if (compatibleCargoTags.isEmpty()) {
      return true;
    }
    return cargoTag != null && compatibleCargoTags.contains(cargoTag.trim().toUpperCase());
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }

  private static String normalizeOptional(String value) {
    return value == null ? "" : value.trim().toUpperCase();
  }
}
