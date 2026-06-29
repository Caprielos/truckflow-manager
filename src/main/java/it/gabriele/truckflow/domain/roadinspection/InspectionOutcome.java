package it.gabriele.truckflow.domain.roadinspection;

/** Esito operativo del controllo su strada. */
public enum InspectionOutcome {
  PASSED,
  WARNING,
  FINE,
  VEHICLE_DETENTION,
  MISSION_BLOCKED
}
