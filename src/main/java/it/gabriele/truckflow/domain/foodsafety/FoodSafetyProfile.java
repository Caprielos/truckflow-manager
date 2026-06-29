package it.gabriele.truckflow.domain.foodsafety;

import java.util.List;

/** Profilo igienico HACCP/alimentare del mezzo. */
public record FoodSafetyProfile(
    String vehicleCode,
    boolean haccpApproved,
    boolean foodGradeSurfaces,
    boolean separatedFromNonFoodCargo,
    boolean cleaningKitAvailable,
    List<SanitationRecord> sanitationRecords) {

  public FoodSafetyProfile {
    vehicleCode = normalize(vehicleCode, "Il codice veicolo è obbligatorio.");
    sanitationRecords = sanitationRecords == null ? List.of() : List.copyOf(sanitationRecords);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
