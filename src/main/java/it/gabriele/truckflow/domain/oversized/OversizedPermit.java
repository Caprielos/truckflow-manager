package it.gabriele.truckflow.domain.oversized;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Weight;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/** Permesso trasporto eccezionale con limiti autorizzati e rotta. */
public record OversizedPermit(
    String permitCode,
    String vehicleCombinationCode,
    Dimension authorizedMaxDimensions,
    Weight authorizedMaxMass,
    LocalDate validFrom,
    LocalDate validUntil,
    Set<String> authorizedCountries,
    EscortRequirement escortRequirement,
    boolean routeApproved,
    boolean communicationObligationSatisfied) {

  public OversizedPermit {
    permitCode = normalize(permitCode, "Il codice permesso eccezionale è obbligatorio.");
    vehicleCombinationCode =
        normalize(vehicleCombinationCode, "Il codice convoglio autorizzato è obbligatorio.");
    Objects.requireNonNull(
        authorizedMaxDimensions, "Le dimensioni massime autorizzate sono obbligatorie.");
    Objects.requireNonNull(authorizedMaxMass, "La massa massima autorizzata è obbligatoria.");
    Objects.requireNonNull(validFrom, "La data inizio permesso è obbligatoria.");
    Objects.requireNonNull(validUntil, "La data fine permesso è obbligatoria.");
    if (validUntil.isBefore(validFrom)) {
      throw new IllegalArgumentException("La fine permesso non può precedere l'inizio.");
    }
    if (authorizedCountries == null || authorizedCountries.isEmpty()) {
      throw new IllegalArgumentException("I paesi autorizzati sono obbligatori.");
    }
    Objects.requireNonNull(escortRequirement, "Il requisito di scorta è obbligatorio.");
    authorizedCountries =
        authorizedCountries.stream()
            .map(country -> normalize(country, "Il paese autorizzato non può essere vuoto."))
            .collect(Collectors.toUnmodifiableSet());
  }

  public boolean isValidOn(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("La data controllo permesso è obbligatoria.");
    }
    return !date.isBefore(validFrom) && !date.isAfter(validUntil);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
