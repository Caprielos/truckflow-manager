package it.gabriele.truckflow.domain.roadinspection;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * Controllo su strada: documentale, tecnico, ADR, ATP, rifiuti, alimentare, animali, eccezionali.
 */
public record RoadInspection(
    String inspectionCode,
    RoadInspectionAuthority authority,
    Instant inspectedAt,
    String vehicleCode,
    String driverCode,
    List<InspectionFinding> findings,
    InspectionOutcome outcome) {

  public RoadInspection {
    inspectionCode = normalize(inspectionCode, "Il codice controllo è obbligatorio.");
    Objects.requireNonNull(authority, "L'autorità controllo è obbligatoria.");
    Objects.requireNonNull(inspectedAt, "La data controllo è obbligatoria.");
    vehicleCode = normalize(vehicleCode, "Il codice veicolo è obbligatorio.");
    driverCode = normalize(driverCode, "Il codice autista è obbligatorio.");
    findings = findings == null ? List.of() : List.copyOf(findings);
    Objects.requireNonNull(outcome, "L'esito controllo è obbligatorio.");
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
