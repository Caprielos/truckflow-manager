package it.gabriele.truckflow.domain.roadinspection;

import java.util.Objects;

/** Regole su blocco mezzo, sanzioni e controlli critici. */
public final class RoadInspectionRules {

  private RoadInspectionRules() {}

  public static boolean hasCriticalFinding(RoadInspection inspection) {
    Objects.requireNonNull(inspection, "Il controllo su strada è obbligatorio.");
    return inspection.findings().stream().anyMatch(InspectionFinding::isCritical);
  }

  public static boolean blocksVehicle(RoadInspection inspection) {
    Objects.requireNonNull(inspection, "Il controllo su strada è obbligatorio.");
    return inspection.outcome() == InspectionOutcome.VEHICLE_DETENTION
        || inspection.outcome() == InspectionOutcome.MISSION_BLOCKED
        || hasCriticalFinding(inspection);
  }

  public static boolean hasFine(RoadInspection inspection) {
    Objects.requireNonNull(inspection, "Il controllo su strada è obbligatorio.");
    return inspection.outcome() == InspectionOutcome.FINE
        || inspection.findings().stream().anyMatch(finding -> finding.fineAmount() != null);
  }
}
