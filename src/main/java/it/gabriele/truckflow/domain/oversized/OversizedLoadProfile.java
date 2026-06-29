package it.gabriele.truckflow.domain.oversized;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Weight;
import java.util.Objects;

/** Carico eccezionale con dimensioni, massa e segnalazioni fisiche. */
public record OversizedLoadProfile(
    String loadCode,
    Dimension actualDimensions,
    Weight actualMass,
    boolean warningPanelsMounted,
    boolean warningLightsMounted,
    boolean routeSurveyRequired) {

  public OversizedLoadProfile {
    loadCode = normalize(loadCode, "Il codice carico eccezionale è obbligatorio.");
    Objects.requireNonNull(actualDimensions, "Le dimensioni carico eccezionale sono obbligatorie.");
    Objects.requireNonNull(actualMass, "La massa carico eccezionale è obbligatoria.");
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
