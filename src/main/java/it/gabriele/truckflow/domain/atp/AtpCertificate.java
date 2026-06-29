package it.gabriele.truckflow.domain.atp;

import it.gabriele.truckflow.domain.shared.TemperatureRange;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/** Certificato ATP del veicolo/allestimento a temperatura controllata. */
public record AtpCertificate(
    String certificateCode,
    String vehicleCode,
    AtpClass atpClass,
    LocalDate validUntil,
    TemperatureRange certifiedTemperatureRange,
    List<TemperatureRecorder> recorders,
    boolean refrigerationUnitMaintenanceValid,
    boolean sanitationRequired) {

  public AtpCertificate {
    certificateCode = normalize(certificateCode, "Il codice certificato ATP è obbligatorio.");
    vehicleCode = normalize(vehicleCode, "Il codice veicolo è obbligatorio.");
    Objects.requireNonNull(atpClass, "La classe ATP è obbligatoria.");
    Objects.requireNonNull(validUntil, "La scadenza ATP è obbligatoria.");
    Objects.requireNonNull(
        certifiedTemperatureRange, "Il range temperatura certificato è obbligatorio.");
    recorders = recorders == null ? List.of() : List.copyOf(recorders);
  }

  public boolean isValidOn(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("La data controllo ATP è obbligatoria.");
    }
    return !validUntil.isBefore(date);
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
