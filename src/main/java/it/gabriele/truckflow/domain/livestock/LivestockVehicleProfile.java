package it.gabriele.truckflow.domain.livestock;

import java.time.LocalDate;
import java.util.Set;

/** Profilo del mezzo per benessere animale. */
public record LivestockVehicleProfile(
    String vehicleCode,
    Set<AnimalSpecies> authorizedSpecies,
    boolean ventilationAvailable,
    boolean wateringSystemAvailable,
    boolean partitionsAvailable,
    boolean cleaningAndDisinfectionValid,
    double usableAreaSquareMeters,
    LocalDate authorizationValidUntil) {

  public LivestockVehicleProfile {
    vehicleCode = normalize(vehicleCode, "Il codice veicolo è obbligatorio.");
    if (authorizedSpecies == null || authorizedSpecies.isEmpty()) {
      throw new IllegalArgumentException("Le specie animali autorizzate sono obbligatorie.");
    }
    if (usableAreaSquareMeters <= 0 || Double.isNaN(usableAreaSquareMeters)) {
      throw new IllegalArgumentException("La superficie utile deve essere maggiore di zero.");
    }
    if (authorizationValidUntil == null) {
      throw new IllegalArgumentException("La scadenza autorizzazione animali è obbligatoria.");
    }
    authorizedSpecies = Set.copyOf(authorizedSpecies);
  }

  public boolean isValidOn(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("La data controllo animali è obbligatoria.");
    }
    return !authorizationValidUntil.isBefore(date);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
