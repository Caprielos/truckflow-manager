package it.gabriele.truckflow.domain.atp;

import java.time.LocalDate;

/** Termografo/sonda temperatura installata su mezzo ATP o frigo. */
public record TemperatureRecorder(
    String serialNumber,
    LocalDate calibratedUntil,
    boolean continuousRecording,
    boolean probeActive) {

  public TemperatureRecorder {
    serialNumber = normalize(serialNumber, "Il seriale termografo è obbligatorio.");
    if (calibratedUntil == null) {
      throw new IllegalArgumentException("La data taratura termografo è obbligatoria.");
    }
  }

  public boolean isValidOn(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("La data di controllo è obbligatoria.");
    }
    return !calibratedUntil.isBefore(date) && continuousRecording && probeActive;
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
