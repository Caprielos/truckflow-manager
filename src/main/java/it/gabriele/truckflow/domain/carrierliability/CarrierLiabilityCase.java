package it.gabriele.truckflow.domain.carrierliability;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;

/** Pratica responsabilità vettore collegata a CMR, danni, ritardi, furti e riserve. */
public record CarrierLiabilityCase(
    String caseCode,
    String shipmentCode,
    LiabilityEventType eventType,
    CarrierResponsibilityStatus status,
    Money estimatedDamageAmount,
    boolean cmrReservationPresent,
    boolean insuranceNotified,
    boolean photosAttached,
    boolean policeReportAttached,
    boolean customerNotified,
    Notes notes) {

  public CarrierLiabilityCase {
    caseCode = normalize(caseCode, "Il codice pratica responsabilità è obbligatorio.");
    shipmentCode = normalize(shipmentCode, "Il codice spedizione è obbligatorio.");
    Objects.requireNonNull(eventType, "Il tipo evento responsabilità è obbligatorio.");
    Objects.requireNonNull(status, "Lo stato responsabilità è obbligatorio.");
    Objects.requireNonNull(estimatedDamageAmount, "L'importo stimato danno è obbligatorio.");
    if (notes == null) {
      notes = Notes.empty();
    }
  }

  public boolean isOpen() {
    return status != CarrierResponsibilityStatus.CLOSED
        && status != CarrierResponsibilityStatus.WAIVED;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
