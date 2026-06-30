package it.gabriele.truckflow.domain.triptemplates;

import java.time.Duration;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public record RouteSpecification(
    Distance totalDistance,
    Duration estimatedDuration,
    Set<String> allowedCountries,
    Set<RouteRoadType> allowedRoadTypes,
    String notes) {

  public RouteSpecification {
    totalDistance = totalDistance == null ? Distance.zeroKm() : totalDistance;
    estimatedDuration =
        TripTemplateValidation.nonNegativeOrNull(estimatedDuration, "estimatedDuration");
    allowedCountries = normalizeCountries(allowedCountries);
    allowedRoadTypes = validateRoadTypes(allowedRoadTypes);
    notes = TripTemplateValidation.normalize(notes);
  }

  public static RouteSpecification empty() {
    return new RouteSpecification(Distance.zeroKm(), null, Set.of(), Set.of(), "");
  }

  public boolean allowsCountry(String country) {
    String normalizedCountry = TripTemplateValidation.requireText(country, "country").toUpperCase();
    return allowedCountries.contains(normalizedCountry);
  }

  public boolean allowsRoadType(RouteRoadType roadType) {
    TripTemplateValidation.requireNonNull(roadType, "roadType");
    return allowedRoadTypes.contains(roadType);
  }

  private static Set<String> normalizeCountries(Set<String> allowedCountries) {
    if (allowedCountries == null || allowedCountries.isEmpty()) {
      return Set.of();
    }

    var normalizedCountries = new TreeSet<String>();
    for (String country : allowedCountries) {
      String normalized =
          TripTemplateValidation.requireText(country, "allowedCountries").toUpperCase(Locale.ROOT);
      normalizedCountries.add(normalized);
    }

    return Set.copyOf(normalizedCountries);
  }

  private static Set<RouteRoadType> validateRoadTypes(Set<RouteRoadType> allowedRoadTypes) {
    if (allowedRoadTypes == null || allowedRoadTypes.isEmpty()) {
      return Set.of();
    }

    TripTemplateValidation.requireNoNullElements(allowedRoadTypes, "allowedRoadTypes");
    return Set.copyOf(allowedRoadTypes);
  }
}
