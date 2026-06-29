package it.gabriele.truckflow.domain.regulation;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/** Profilo normativo caricato per un paese europeo. */
public record CountryRegulatoryProfile(
    EuropeanCountry country,
    boolean configured,
    Set<RegulatoryRequirement> requirements,
    Set<RegulatoryIntegrationSystem> integrationSystems) {

  public CountryRegulatoryProfile {
    Objects.requireNonNull(country, "Il paese del profilo normativo è obbligatorio.");
    requirements = requirements == null ? Set.of() : Set.copyOf(requirements);
    integrationSystems = integrationSystems == null ? Set.of() : Set.copyOf(integrationSystems);
    if (configured && requirements.isEmpty()) {
      throw new IllegalArgumentException(
          "Un profilo normativo configurato deve avere almeno un requisito.");
    }
  }

  public boolean requires(RegulatoryRequirementCode code) {
    Objects.requireNonNull(code, "Il codice requisito da verificare è obbligatorio.");
    return requirements.stream().anyMatch(requirement -> requirement.code() == code);
  }

  public Set<RegulatoryRequirement> requirementsFor(RegulatoryArea area) {
    Objects.requireNonNull(area, "L'area normativa da filtrare è obbligatoria.");
    return requirements.stream()
        .filter(requirement -> requirement.area() == area)
        .collect(Collectors.toUnmodifiableSet());
  }

  public Set<RegulatoryRequirement> blockingRequirements() {
    return requirements.stream()
        .filter(RegulatoryRequirement::blocksOperation)
        .collect(Collectors.toUnmodifiableSet());
  }

  public boolean hasIntegration(RegulatoryIntegrationSystem system) {
    Objects.requireNonNull(system, "Il sistema di integrazione è obbligatorio.");
    return integrationSystems.contains(system);
  }
}
