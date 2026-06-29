package it.gabriele.truckflow.domain.foodsafety;

import java.time.Instant;
import java.util.Objects;

/** Regole HACCP/alimentare: pulizia, sanificazione, separazione e temperatura. */
public final class FoodSafetyRules {

  private FoodSafetyRules() {}

  public static boolean hasValidSanitation(FoodSafetyProfile profile, Instant instant) {
    Objects.requireNonNull(profile, "Il profilo alimentare è obbligatorio.");
    return profile.sanitationRecords().stream().anyMatch(record -> record.isValidAt(instant));
  }

  public static boolean canLoadFood(
      FoodSafetyProfile profile, FoodProductType productType, Instant instant) {
    Objects.requireNonNull(profile, "Il profilo alimentare è obbligatorio.");
    Objects.requireNonNull(productType, "Il tipo prodotto alimentare è obbligatorio.");
    return profile.haccpApproved()
        && profile.foodGradeSurfaces()
        && profile.separatedFromNonFoodCargo()
        && profile.cleaningKitAvailable()
        && hasValidSanitation(profile, instant);
  }

  public static boolean requiresTemperatureRecorder(FoodProductType productType) {
    Objects.requireNonNull(productType, "Il tipo prodotto alimentare è obbligatorio.");
    return productType.requiresTemperatureControl();
  }
}
