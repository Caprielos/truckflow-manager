package it.gabriele.truckflow.domain.waste;

import java.time.LocalDate;
import java.util.Set;

/** Iscrizione/autorizzazione ambientale per azienda, categorie e mezzi autorizzati. */
public record EnvironmentalManagerRegistration(
    String registrationCode,
    String companyCode,
    Set<WasteCategory> authorizedCategories,
    Set<String> authorizedVehicleCodes,
    LocalDate validUntil) {

  public EnvironmentalManagerRegistration {
    registrationCode =
        normalize(registrationCode, "Il codice iscrizione ambientale è obbligatorio.");
    companyCode = normalize(companyCode, "Il codice azienda è obbligatorio.");
    if (authorizedCategories == null || authorizedCategories.isEmpty()) {
      throw new IllegalArgumentException("Le categorie rifiuto autorizzate sono obbligatorie.");
    }
    if (authorizedVehicleCodes == null || authorizedVehicleCodes.isEmpty()) {
      throw new IllegalArgumentException("I mezzi autorizzati sono obbligatori.");
    }
    if (validUntil == null) {
      throw new IllegalArgumentException("La scadenza iscrizione ambientale è obbligatoria.");
    }
    authorizedCategories = Set.copyOf(authorizedCategories);
    authorizedVehicleCodes =
        authorizedVehicleCodes.stream()
            .map(EnvironmentalManagerRegistration::upper)
            .collect(java.util.stream.Collectors.toUnmodifiableSet());
  }

  public boolean isValidOn(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException(
          "La data controllo autorizzazione rifiuti è obbligatoria.");
    }
    return !validUntil.isBefore(date);
  }

  public boolean authorizes(String vehicleCode, WasteCategory category, LocalDate date) {
    return isValidOn(date)
        && authorizedVehicleCodes.contains(upper(vehicleCode))
        && authorizedCategories.contains(category);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return upper(value);
  }

  private static String upper(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Il valore non può essere vuoto.");
    }
    return value.trim().toUpperCase();
  }
}
