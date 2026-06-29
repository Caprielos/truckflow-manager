package it.gabriele.truckflow.domain.roadinspection;

import it.gabriele.truckflow.domain.shared.Money;
import java.util.Objects;

/** Non conformità, sanzione o blocco rilevati da un controllo. */
public record InspectionFinding(
    InspectionFindingType type,
    RoadInspectionFindingSeverity severity,
    String description,
    Money fineAmount,
    boolean vehicleDetentionRequired) {

  public InspectionFinding {
    Objects.requireNonNull(type, "Il tipo rilievo controllo è obbligatorio.");
    Objects.requireNonNull(severity, "La gravità rilievo controllo è obbligatoria.");
    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("La descrizione rilievo controllo è obbligatoria.");
    }
    description = description.trim();
  }

  public boolean isCritical() {
    return severity == RoadInspectionFindingSeverity.CRITICAL || vehicleDetentionRequired;
  }
}
