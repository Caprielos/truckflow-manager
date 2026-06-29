package it.gabriele.truckflow.domain.alerting;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Objects;

/** Alert operativo generato dal sistema o da una regola enterprise. */
public final class AlertEvent {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_TITLE_LENGTH = 150;
  private static final int MAX_MESSAGE_LENGTH = 4000;

  private final String alertCode;
  private final AlertType type;
  private final AlertSeverity severity;
  private final AlertSourceType sourceType;
  private final String sourceCode;
  private final String title;
  private final String message;
  private final Instant raisedAt;
  private final Instant acknowledgedAt;
  private final Instant resolvedAt;
  private final AlertStatus status;
  private final Notes notes;

  private AlertEvent(
      String alertCode,
      AlertType type,
      AlertSeverity severity,
      AlertSourceType sourceType,
      String sourceCode,
      String title,
      String message,
      Instant raisedAt,
      Instant acknowledgedAt,
      Instant resolvedAt,
      AlertStatus status,
      Notes notes) {
    this.alertCode = validateCode(alertCode, "Il codice alert è obbligatorio.");

    if (type == null) {
      throw new IllegalArgumentException("Il tipo alert è obbligatorio.");
    }

    if (severity == null) {
      throw new IllegalArgumentException("La gravità alert è obbligatoria.");
    }

    if (sourceType == null) {
      throw new IllegalArgumentException("Il tipo sorgente alert è obbligatorio.");
    }

    this.sourceCode = validateCode(sourceCode, "Il codice sorgente alert è obbligatorio.");
    this.title = validateText(title, MAX_TITLE_LENGTH, "Il titolo alert è obbligatorio.");
    this.message = validateText(message, MAX_MESSAGE_LENGTH, "Il messaggio alert è obbligatorio.");

    if (raisedAt == null) {
      throw new IllegalArgumentException("La data creazione alert è obbligatoria.");
    }

    if (status == null) {
      throw new IllegalArgumentException("Lo stato alert è obbligatorio.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note alert sono obbligatorie.");
    }

    validateLifecycle(raisedAt, acknowledgedAt, resolvedAt, status);

    this.type = type;
    this.severity = severity;
    this.sourceType = sourceType;
    this.raisedAt = raisedAt;
    this.acknowledgedAt = acknowledgedAt;
    this.resolvedAt = resolvedAt;
    this.status = status;
    this.notes = notes;
  }

  public static AlertEvent open(
      String alertCode,
      AlertType type,
      AlertSeverity severity,
      AlertSourceType sourceType,
      String sourceCode,
      String title,
      String message,
      Instant raisedAt,
      Notes notes) {
    return new AlertEvent(
        alertCode,
        type,
        severity,
        sourceType,
        sourceCode,
        title,
        message,
        raisedAt,
        null,
        null,
        AlertStatus.OPEN,
        notes);
  }

  private static void validateLifecycle(
      Instant raisedAt, Instant acknowledgedAt, Instant resolvedAt, AlertStatus status) {
    if (acknowledgedAt != null && acknowledgedAt.isBefore(raisedAt)) {
      throw new IllegalArgumentException(
          "La presa in carico alert non può precedere la creazione.");
    }

    if (resolvedAt != null && resolvedAt.isBefore(raisedAt)) {
      throw new IllegalArgumentException("La risoluzione alert non può precedere la creazione.");
    }

    if ((status == AlertStatus.RESOLVED || status == AlertStatus.DISMISSED) && resolvedAt == null) {
      throw new IllegalArgumentException("Un alert chiuso richiede una data di chiusura.");
    }
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

  private static String validateText(String text, int maxLength, String nullMessage) {
    if (text == null) {
      throw new IllegalArgumentException(nullMessage);
    }

    String normalizedText = text.trim();

    if (normalizedText.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }

    if (normalizedText.length() > maxLength) {
      throw new IllegalArgumentException("Il testo non può superare " + maxLength + " caratteri.");
    }

    return normalizedText;
  }

  public AlertEvent acknowledge(Instant acknowledgedAt) {
    if (!AlertRules.canBeAcknowledged(this)) {
      throw new IllegalStateException("L'alert non può essere preso in carico.");
    }

    return new AlertEvent(
        alertCode,
        type,
        severity,
        sourceType,
        sourceCode,
        title,
        message,
        raisedAt,
        acknowledgedAt,
        null,
        AlertStatus.ACKNOWLEDGED,
        notes);
  }

  public AlertEvent resolve(Instant resolvedAt, Notes resolutionNotes) {
    if (!AlertRules.canBeClosed(this)) {
      throw new IllegalStateException("L'alert non può essere risolto.");
    }

    if (resolutionNotes == null || resolutionNotes.isEmpty()) {
      throw new IllegalArgumentException("Le note di risoluzione alert sono obbligatorie.");
    }

    return new AlertEvent(
        alertCode,
        type,
        severity,
        sourceType,
        sourceCode,
        title,
        message,
        raisedAt,
        acknowledgedAt,
        resolvedAt,
        AlertStatus.RESOLVED,
        resolutionNotes);
  }

  public String getAlertCode() {
    return alertCode;
  }

  public AlertType getType() {
    return type;
  }

  public AlertSeverity getSeverity() {
    return severity;
  }

  public AlertSourceType getSourceType() {
    return sourceType;
  }

  public String getSourceCode() {
    return sourceCode;
  }

  public String getTitle() {
    return title;
  }

  public String getMessage() {
    return message;
  }

  public Instant getRaisedAt() {
    return raisedAt;
  }

  public Instant getAcknowledgedAt() {
    return acknowledgedAt;
  }

  public Instant getResolvedAt() {
    return resolvedAt;
  }

  public AlertStatus getStatus() {
    return status;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isActive() {
    return status.isActive();
  }

  public boolean requiresEscalation() {
    return severity.requiresEscalation();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AlertEvent that)) return false;
    return alertCode.equals(that.alertCode)
        && type == that.type
        && severity == that.severity
        && sourceType == that.sourceType
        && sourceCode.equals(that.sourceCode)
        && title.equals(that.title)
        && message.equals(that.message)
        && raisedAt.equals(that.raisedAt)
        && Objects.equals(acknowledgedAt, that.acknowledgedAt)
        && Objects.equals(resolvedAt, that.resolvedAt)
        && status == that.status
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        alertCode,
        type,
        severity,
        sourceType,
        sourceCode,
        title,
        message,
        raisedAt,
        acknowledgedAt,
        resolvedAt,
        status,
        notes);
  }
}
