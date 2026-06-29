package it.gabriele.truckflow.domain.atp;

import it.gabriele.truckflow.domain.shared.TemperatureRange;
import java.time.LocalDate;
import java.util.Objects;

/** Regole ATP per temperatura controllata, termografi e idoneità frigo. */
public final class AtpRules {

  private AtpRules() {}

  public static boolean canCarryTemperatureRange(
      AtpCertificate certificate, TemperatureRange requiredRange, LocalDate date) {
    Objects.requireNonNull(certificate, "Il certificato ATP è obbligatorio.");
    Objects.requireNonNull(requiredRange, "Il range temperatura richiesto è obbligatorio.");
    return certificate.isValidOn(date)
        && certificate.refrigerationUnitMaintenanceValid()
        && requiredRange.isCoveredBy(certificate.certifiedTemperatureRange());
  }

  public static boolean hasValidTemperatureRecorder(AtpCertificate certificate, LocalDate date) {
    Objects.requireNonNull(certificate, "Il certificato ATP è obbligatorio.");
    return certificate.recorders().stream().anyMatch(recorder -> recorder.isValidOn(date));
  }

  public static boolean isReadyForFoodOrPharmaTransport(
      AtpCertificate certificate, TemperatureRange requiredRange, LocalDate date) {
    return canCarryTemperatureRange(certificate, requiredRange, date)
        && hasValidTemperatureRecorder(certificate, date);
  }

  public static boolean canCarryFrozenGoods(AtpCertificate certificate, LocalDate date) {
    Objects.requireNonNull(certificate, "Il certificato ATP è obbligatorio.");
    return certificate.isValidOn(date)
        && certificate.atpClass().isFrozenTransportCapable()
        && certificate.refrigerationUnitMaintenanceValid();
  }
}
