package it.gabriele.truckflow.domain.hr;

import java.time.LocalDate;

/** Regole HR per idoneità, formazione e attenzione operativa autisti. */
public final class DriverHrRules {

  private DriverHrRules() {}

  public static boolean canBeAssignedToMission(DriverMedicalCheck medicalCheck) {
    if (medicalCheck == null) {
      throw new IllegalArgumentException("La visita medica è obbligatoria.");
    }

    return medicalCheck.canDrive();
  }

  public static boolean trainingIsExpired(DriverTrainingRecord training, LocalDate today) {
    if (training == null) {
      throw new IllegalArgumentException("La formazione è obbligatoria.");
    }

    if (today == null) {
      throw new IllegalArgumentException("La data di riferimento è obbligatoria.");
    }

    return training.getExpiresOn() != null && today.isAfter(training.getExpiresOn());
  }
}
