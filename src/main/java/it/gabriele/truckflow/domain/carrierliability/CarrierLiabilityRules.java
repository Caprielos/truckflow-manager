package it.gabriele.truckflow.domain.carrierliability;

import java.util.Objects;

/** Regole CMR/claim per responsabilità vettore, assicurazione e documentazione del danno. */
public final class CarrierLiabilityRules {

  private CarrierLiabilityRules() {}

  public static boolean requiresInsuranceNotification(CarrierLiabilityCase liabilityCase) {
    Objects.requireNonNull(liabilityCase, "La pratica responsabilità è obbligatoria.");
    return liabilityCase.estimatedDamageAmount().getAmount().signum() > 0
        && liabilityCase.eventType() != LiabilityEventType.MISSING_POD;
  }

  public static boolean isReadyForAssessment(CarrierLiabilityCase liabilityCase) {
    Objects.requireNonNull(liabilityCase, "La pratica responsabilità è obbligatoria.");
    return liabilityCase.customerNotified()
        && liabilityCase.photosAttached()
        && (!requiresInsuranceNotification(liabilityCase) || liabilityCase.insuranceNotified());
  }

  public static boolean affectsQualityKpi(CarrierLiabilityCase liabilityCase) {
    Objects.requireNonNull(liabilityCase, "La pratica responsabilità è obbligatoria.");
    return liabilityCase.isOpen()
        || liabilityCase.status() == CarrierResponsibilityStatus.CARRIER_RESPONSIBLE;
  }

  public static boolean requiresPoliceReport(CarrierLiabilityCase liabilityCase) {
    Objects.requireNonNull(liabilityCase, "La pratica responsabilità è obbligatoria.");
    return liabilityCase.eventType() == LiabilityEventType.THEFT
        || liabilityCase.eventType() == LiabilityEventType.ROAD_ACCIDENT
        || liabilityCase.eventType() == LiabilityEventType.ADR_SPILL;
  }
}
