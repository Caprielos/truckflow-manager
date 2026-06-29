package it.gabriele.truckflow.domain.livestock;

import java.time.Duration;

/** Piano viaggio animali con documenti, spazio minimo e pulizia. */
public record LivestockTripPlan(
    String tripCode,
    AnimalSpecies species,
    int animalCount,
    double requiredAreaPerAnimalSquareMeters,
    Duration plannedDuration,
    boolean veterinaryDocumentsPresent,
    boolean routeRestStopsPlanned,
    boolean cleaningDisinfectionPlanned) {

  public LivestockTripPlan {
    tripCode = normalize(tripCode, "Il codice viaggio animali è obbligatorio.");
    if (species == null) {
      throw new IllegalArgumentException("La specie animale è obbligatoria.");
    }
    if (animalCount <= 0) {
      throw new IllegalArgumentException("Il numero animali deve essere maggiore di zero.");
    }
    if (requiredAreaPerAnimalSquareMeters <= 0 || Double.isNaN(requiredAreaPerAnimalSquareMeters)) {
      throw new IllegalArgumentException("Lo spazio minimo per animale è obbligatorio.");
    }
    if (plannedDuration == null || plannedDuration.isNegative() || plannedDuration.isZero()) {
      throw new IllegalArgumentException("La durata pianificata deve essere positiva.");
    }
  }

  public double requiredTotalAreaSquareMeters() {
    return animalCount * requiredAreaPerAnimalSquareMeters;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
