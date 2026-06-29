package it.gabriele.truckflow.domain.routeoptimization;

/** Vincolo tecnico, legale o operativo di un percorso. */
public record RouteConstraint(
    String constraintCode,
    RouteConstraintType constraintType,
    RouteConstraintSeverity severity,
    String description,
    boolean satisfied) {

  public RouteConstraint {
    constraintCode = normalize(constraintCode, "Il codice vincolo è obbligatorio.");
    if (constraintType == null) {
      throw new IllegalArgumentException("Il tipo vincolo è obbligatorio.");
    }
    if (severity == null) {
      throw new IllegalArgumentException("La severità vincolo è obbligatoria.");
    }
    description = normalize(description, "La descrizione vincolo è obbligatoria.");
  }

  public boolean blocksRoute() {
    return !satisfied
        && (severity == RouteConstraintSeverity.BLOCKING
            || severity == RouteConstraintSeverity.LEGAL_BLOCKING);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
