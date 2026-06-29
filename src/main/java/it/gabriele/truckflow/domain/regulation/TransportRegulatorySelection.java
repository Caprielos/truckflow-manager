package it.gabriele.truckflow.domain.regulation;

import java.time.LocalDate;
import java.util.Objects;

/** Configurazione normativa scelta all'avvio o nelle impostazioni aziendali. */
public record TransportRegulatorySelection(
    String tenantCode,
    EuropeanCountry activeCountry,
    CountryRegulatoryProfile activeProfile,
    LocalDate activatedOn) {

  public TransportRegulatorySelection {
    tenantCode = normalize(tenantCode, "Il codice tenant/azienda è obbligatorio.");
    Objects.requireNonNull(activeCountry, "Il paese attivo è obbligatorio.");
    Objects.requireNonNull(activeProfile, "Il profilo normativo attivo è obbligatorio.");
    Objects.requireNonNull(activatedOn, "La data attivazione profilo è obbligatoria.");
    if (activeProfile.country() != activeCountry) {
      throw new IllegalArgumentException("Il profilo normativo deve appartenere al paese attivo.");
    }
  }

  public static TransportRegulatorySelection startWithCountry(
      String tenantCode, EuropeanCountry country, LocalDate activatedOn) {
    return new TransportRegulatorySelection(
        tenantCode, country, RoadTransportRegulationCatalog.forCountry(country), activatedOn);
  }

  public TransportRegulatorySelection changeCountry(
      EuropeanCountry newCountry, LocalDate activatedOn) {
    return startWithCountry(tenantCode, newCountry, activatedOn);
  }

  public boolean isFullyConfigured() {
    return activeProfile.configured();
  }

  public boolean requires(RegulatoryRequirementCode code) {
    return activeProfile.requires(code);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
