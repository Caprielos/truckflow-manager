package it.gabriele.truckflow.domain.hr;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.Objects;

/** Formazione, corso o abilitazione interna collegata a un autista. */
public final class DriverTrainingRecord {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_TITLE_LENGTH = 150;

  private final String trainingCode;
  private final String driverCode;
  private final DriverTrainingType type;
  private final String title;
  private final LocalDate completedOn;
  private final LocalDate expiresOn;
  private final DriverTrainingStatus status;
  private final Notes notes;

  private DriverTrainingRecord(
      String trainingCode,
      String driverCode,
      DriverTrainingType type,
      String title,
      LocalDate completedOn,
      LocalDate expiresOn,
      DriverTrainingStatus status,
      Notes notes) {
    this.trainingCode = validateCode(trainingCode, "Il codice formazione è obbligatorio.");
    this.driverCode = validateCode(driverCode, "Il codice autista è obbligatorio.");

    if (type == null) {
      throw new IllegalArgumentException("Il tipo formazione è obbligatorio.");
    }

    this.title = validateTitle(title);

    if (status == null) {
      throw new IllegalArgumentException("Lo stato formazione è obbligatorio.");
    }

    if (completedOn != null && expiresOn != null && expiresOn.isBefore(completedOn)) {
      throw new IllegalArgumentException(
          "La scadenza formazione non può precedere il completamento.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note formazione sono obbligatorie.");
    }

    this.type = type;
    this.completedOn = completedOn;
    this.expiresOn = expiresOn;
    this.status = status;
    this.notes = notes;
  }

  public static DriverTrainingRecord planned(
      String trainingCode, String driverCode, DriverTrainingType type, String title, Notes notes) {
    return new DriverTrainingRecord(
        trainingCode, driverCode, type, title, null, null, DriverTrainingStatus.PLANNED, notes);
  }

  public static DriverTrainingRecord completed(
      String trainingCode,
      String driverCode,
      DriverTrainingType type,
      String title,
      LocalDate completedOn,
      LocalDate expiresOn,
      Notes notes) {
    if (completedOn == null) {
      throw new IllegalArgumentException("La data completamento formazione è obbligatoria.");
    }

    return new DriverTrainingRecord(
        trainingCode,
        driverCode,
        type,
        title,
        completedOn,
        expiresOn,
        DriverTrainingStatus.COMPLETED,
        notes);
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

  private static String validateTitle(String title) {
    if (title == null) {
      throw new IllegalArgumentException("Il titolo formazione è obbligatorio.");
    }

    String normalizedTitle = title.trim();

    if (normalizedTitle.isEmpty()) {
      throw new IllegalArgumentException("Il titolo formazione non può essere vuoto.");
    }

    if (normalizedTitle.length() > MAX_TITLE_LENGTH) {
      throw new IllegalArgumentException(
          "Il titolo formazione non può superare " + MAX_TITLE_LENGTH + " caratteri.");
    }

    return normalizedTitle;
  }

  public DriverTrainingRecord expire() {
    return new DriverTrainingRecord(
        trainingCode,
        driverCode,
        type,
        title,
        completedOn,
        expiresOn,
        DriverTrainingStatus.EXPIRED,
        notes);
  }

  public String getTrainingCode() {
    return trainingCode;
  }

  public String getDriverCode() {
    return driverCode;
  }

  public DriverTrainingType getType() {
    return type;
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getCompletedOn() {
    return completedOn;
  }

  public LocalDate getExpiresOn() {
    return expiresOn;
  }

  public DriverTrainingStatus getStatus() {
    return status;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean requiresAttention() {
    return status.requiresAttention();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DriverTrainingRecord that)) return false;
    return trainingCode.equals(that.trainingCode)
        && driverCode.equals(that.driverCode)
        && type == that.type
        && title.equals(that.title)
        && Objects.equals(completedOn, that.completedOn)
        && Objects.equals(expiresOn, that.expiresOn)
        && status == that.status
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        trainingCode, driverCode, type, title, completedOn, expiresOn, status, notes);
  }
}
