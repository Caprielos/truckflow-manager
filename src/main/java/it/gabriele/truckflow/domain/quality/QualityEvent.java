package it.gabriele.truckflow.domain.quality;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Objects;

/** Evento qualità, reclamo servizio o non conformità operativa. */
public final class QualityEvent {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_TITLE_LENGTH = 150;

  private final String eventCode;
  private final QualityEventType type;
  private final QualitySeverity severity;
  private final String referenceCode;
  private final String title;
  private final Instant openedAt;
  private final Instant closedAt;
  private final QualityStatus status;
  private final CorrectiveAction correctiveAction;
  private final Notes notes;

  private QualityEvent(
      String eventCode,
      QualityEventType type,
      QualitySeverity severity,
      String referenceCode,
      String title,
      Instant openedAt,
      Instant closedAt,
      QualityStatus status,
      CorrectiveAction correctiveAction,
      Notes notes) {
    this.eventCode = validateCode(eventCode, "Il codice evento qualità è obbligatorio.");

    if (type == null) {
      throw new IllegalArgumentException("Il tipo evento qualità è obbligatorio.");
    }

    if (severity == null) {
      throw new IllegalArgumentException("La gravità evento qualità è obbligatoria.");
    }

    this.referenceCode =
        validateCode(referenceCode, "Il riferimento evento qualità è obbligatorio.");
    this.title = validateTitle(title);

    if (openedAt == null) {
      throw new IllegalArgumentException("La data apertura evento qualità è obbligatoria.");
    }

    if (status == null) {
      throw new IllegalArgumentException("Lo stato evento qualità è obbligatorio.");
    }

    if (!status.isActive() && closedAt == null) {
      throw new IllegalArgumentException("Un evento qualità chiuso richiede data chiusura.");
    }

    if (closedAt != null && closedAt.isBefore(openedAt)) {
      throw new IllegalArgumentException("La chiusura qualità non può precedere l'apertura.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note evento qualità sono obbligatorie.");
    }

    this.type = type;
    this.severity = severity;
    this.openedAt = openedAt;
    this.closedAt = closedAt;
    this.status = status;
    this.correctiveAction = correctiveAction;
    this.notes = notes;
  }

  public static QualityEvent open(
      String eventCode,
      QualityEventType type,
      QualitySeverity severity,
      String referenceCode,
      String title,
      Instant openedAt,
      Notes notes) {
    return new QualityEvent(
        eventCode,
        type,
        severity,
        referenceCode,
        title,
        openedAt,
        null,
        QualityStatus.OPEN,
        null,
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
      throw new IllegalArgumentException("Il titolo evento qualità è obbligatorio.");
    }

    String normalizedTitle = title.trim();

    if (normalizedTitle.isEmpty()) {
      throw new IllegalArgumentException("Il titolo evento qualità non può essere vuoto.");
    }

    if (normalizedTitle.length() > MAX_TITLE_LENGTH) {
      throw new IllegalArgumentException(
          "Il titolo evento qualità non può superare " + MAX_TITLE_LENGTH + " caratteri.");
    }

    return normalizedTitle;
  }

  public QualityEvent assignCorrectiveAction(CorrectiveAction correctiveAction) {
    if (!QualityRules.canAssignCorrectiveAction(this)) {
      throw new IllegalStateException("Non è possibile assegnare azione correttiva.");
    }

    if (correctiveAction == null) {
      throw new IllegalArgumentException("L'azione correttiva è obbligatoria.");
    }

    return new QualityEvent(
        eventCode,
        type,
        severity,
        referenceCode,
        title,
        openedAt,
        null,
        QualityStatus.CORRECTIVE_ACTION_ASSIGNED,
        correctiveAction,
        notes);
  }

  public QualityEvent close(Instant closedAt) {
    if (!QualityRules.canBeClosed(this)) {
      throw new IllegalStateException("L'evento qualità non può essere chiuso.");
    }

    return new QualityEvent(
        eventCode,
        type,
        severity,
        referenceCode,
        title,
        openedAt,
        closedAt,
        QualityStatus.CLOSED,
        correctiveAction,
        notes);
  }

  public String getEventCode() {
    return eventCode;
  }

  public QualityEventType getType() {
    return type;
  }

  public QualitySeverity getSeverity() {
    return severity;
  }

  public String getReferenceCode() {
    return referenceCode;
  }

  public String getTitle() {
    return title;
  }

  public Instant getOpenedAt() {
    return openedAt;
  }

  public Instant getClosedAt() {
    return closedAt;
  }

  public QualityStatus getStatus() {
    return status;
  }

  public CorrectiveAction getCorrectiveAction() {
    return correctiveAction;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isActive() {
    return status.isActive();
  }

  public boolean requiresManagementReview() {
    return severity.requiresManagementReview();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof QualityEvent that)) return false;
    return eventCode.equals(that.eventCode)
        && type == that.type
        && severity == that.severity
        && referenceCode.equals(that.referenceCode)
        && title.equals(that.title)
        && openedAt.equals(that.openedAt)
        && Objects.equals(closedAt, that.closedAt)
        && status == that.status
        && Objects.equals(correctiveAction, that.correctiveAction)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        eventCode,
        type,
        severity,
        referenceCode,
        title,
        openedAt,
        closedAt,
        status,
        correctiveAction,
        notes);
  }
}
