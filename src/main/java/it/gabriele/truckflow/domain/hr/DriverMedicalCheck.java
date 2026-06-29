package it.gabriele.truckflow.domain.hr;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.Objects;

/** Visita medica o idoneità alla mansione per un autista. */
public final class DriverMedicalCheck {

  private static final int MAX_CODE_LENGTH = 50;

  private final String checkCode;
  private final String driverCode;
  private final LocalDate performedOn;
  private final LocalDate expiresOn;
  private final DriverMedicalCheckStatus status;
  private final Notes notes;

  private DriverMedicalCheck(
      String checkCode,
      String driverCode,
      LocalDate performedOn,
      LocalDate expiresOn,
      DriverMedicalCheckStatus status,
      Notes notes) {
    this.checkCode = validateCode(checkCode, "Il codice visita medica è obbligatorio.");
    this.driverCode = validateCode(driverCode, "Il codice autista è obbligatorio.");

    if (status == null) {
      throw new IllegalArgumentException("Lo stato visita medica è obbligatorio.");
    }

    if ((status == DriverMedicalCheckStatus.FIT
            || status == DriverMedicalCheckStatus.FIT_WITH_LIMITATIONS
            || status == DriverMedicalCheckStatus.NOT_FIT)
        && performedOn == null) {
      throw new IllegalArgumentException("Una visita medica valutata richiede la data esecuzione.");
    }

    if (performedOn != null && expiresOn != null && expiresOn.isBefore(performedOn)) {
      throw new IllegalArgumentException("La scadenza visita medica non può precedere la visita.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note visita medica sono obbligatorie.");
    }

    this.performedOn = performedOn;
    this.expiresOn = expiresOn;
    this.status = status;
    this.notes = notes;
  }

  public static DriverMedicalCheck scheduled(
      String checkCode, String driverCode, LocalDate scheduledDate, Notes notes) {
    return new DriverMedicalCheck(
        checkCode, driverCode, scheduledDate, null, DriverMedicalCheckStatus.SCHEDULED, notes);
  }

  public static DriverMedicalCheck fit(
      String checkCode,
      String driverCode,
      LocalDate performedOn,
      LocalDate expiresOn,
      Notes notes) {
    return new DriverMedicalCheck(
        checkCode, driverCode, performedOn, expiresOn, DriverMedicalCheckStatus.FIT, notes);
  }

  public static DriverMedicalCheck notFit(
      String checkCode, String driverCode, LocalDate performedOn, Notes notes) {
    return new DriverMedicalCheck(
        checkCode, driverCode, performedOn, null, DriverMedicalCheckStatus.NOT_FIT, notes);
  }

  private static String validateCode(String code, String nullMessage) {
    if (code == null) {
      throw new IllegalArgumentException(nullMessage);
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  public String getCheckCode() {
    return checkCode;
  }

  public String getDriverCode() {
    return driverCode;
  }

  public LocalDate getPerformedOn() {
    return performedOn;
  }

  public LocalDate getExpiresOn() {
    return expiresOn;
  }

  public DriverMedicalCheckStatus getStatus() {
    return status;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean canDrive() {
    return status.canDrive();
  }

  public boolean requiresAttention() {
    return status.requiresAttention();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DriverMedicalCheck that)) return false;
    return checkCode.equals(that.checkCode)
        && driverCode.equals(that.driverCode)
        && Objects.equals(performedOn, that.performedOn)
        && Objects.equals(expiresOn, that.expiresOn)
        && status == that.status
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(checkCode, driverCode, performedOn, expiresOn, status, notes);
  }
}
