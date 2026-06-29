package it.gabriele.truckflow.domain.oversized;

import java.time.LocalDate;
import java.util.Objects;

/** Regole per permessi, scorte, pannelli, luci e rotta del trasporto eccezionale. */
public final class OversizedTransportRules {

  private OversizedTransportRules() {}

  public static boolean permitCoversLoad(OversizedPermit permit, OversizedLoadProfile load) {
    Objects.requireNonNull(permit, "Il permesso eccezionale è obbligatorio.");
    Objects.requireNonNull(load, "Il carico eccezionale è obbligatorio.");
    return load.actualDimensions().fitsInside(permit.authorizedMaxDimensions())
        && load.actualMass().isLessThanOrEqualTo(permit.authorizedMaxMass());
  }

  public static boolean canDepart(
      OversizedPermit permit, OversizedLoadProfile load, LocalDate date, String countryCode) {
    return permit.isValidOn(date)
        && permit.authorizedCountries().contains(normalize(countryCode))
        && permit.routeApproved()
        && permit.communicationObligationSatisfied()
        && permitCoversLoad(permit, load)
        && load.warningPanelsMounted()
        && load.warningLightsMounted()
        && (!load.routeSurveyRequired() || permit.routeApproved());
  }

  public static boolean requiresEscort(OversizedPermit permit) {
    Objects.requireNonNull(permit, "Il permesso eccezionale è obbligatorio.");
    return permit.escortRequirement() != EscortRequirement.NONE;
  }

  private static String normalize(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Il codice paese è obbligatorio.");
    }
    return value.trim().toUpperCase();
  }
}
