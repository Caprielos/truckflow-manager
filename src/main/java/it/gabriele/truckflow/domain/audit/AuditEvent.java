package it.gabriele.truckflow.domain.audit;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Objects;

/** Evento audit immutabile. Registra chi ha fatto cosa, quando e su quale aggregato. */
public final class AuditEvent {

  private static final int MAX_CODE_LENGTH = 80;

  private final String eventId;
  private final String aggregateType;
  private final String aggregateId;
  private final AuditActorType actorType;
  private final String actorId;
  private final AuditActionType actionType;
  private final AuditSeverity severity;
  private final Instant occurredAt;
  private final Notes notes;

  private AuditEvent(
      String eventId,
      String aggregateType,
      String aggregateId,
      AuditActorType actorType,
      String actorId,
      AuditActionType actionType,
      AuditSeverity severity,
      Instant occurredAt,
      Notes notes) {
    this.eventId = validateCode(eventId, "L'id evento audit è obbligatorio.");
    this.aggregateType = validateCode(aggregateType, "Il tipo aggregato audit è obbligatorio.");
    this.aggregateId = validateCode(aggregateId, "L'id aggregato audit è obbligatorio.");

    if (actorType == null) {
      throw new IllegalArgumentException("Il tipo attore audit è obbligatorio.");
    }

    this.actorId = validateCode(actorId, "L'id attore audit è obbligatorio.");

    if (actionType == null) {
      throw new IllegalArgumentException("Il tipo azione audit è obbligatorio.");
    }

    if (severity == null) {
      throw new IllegalArgumentException("La gravità audit è obbligatoria.");
    }

    if (occurredAt == null) {
      throw new IllegalArgumentException("La data e ora audit sono obbligatorie.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note audit sono obbligatorie.");
    }

    this.actorType = actorType;
    this.actionType = actionType;
    this.severity = severity;
    this.occurredAt = occurredAt;
    this.notes = notes;
  }

  public static AuditEvent of(
      String eventId,
      String aggregateType,
      String aggregateId,
      AuditActorType actorType,
      String actorId,
      AuditActionType actionType,
      AuditSeverity severity,
      Instant occurredAt,
      Notes notes) {
    return new AuditEvent(
        eventId,
        aggregateType,
        aggregateId,
        actorType,
        actorId,
        actionType,
        severity,
        occurredAt,
        notes);
  }

  public static AuditEvent userAction(
      String eventId,
      String aggregateType,
      String aggregateId,
      String userId,
      AuditActionType actionType,
      Instant occurredAt,
      Notes notes) {
    return of(
        eventId,
        aggregateType,
        aggregateId,
        AuditActorType.USER,
        userId,
        actionType,
        AuditSeverity.INFO,
        occurredAt,
        notes);
  }

  public static AuditEvent systemAction(
      String eventId,
      String aggregateType,
      String aggregateId,
      AuditActionType actionType,
      Instant occurredAt,
      Notes notes) {
    return of(
        eventId,
        aggregateType,
        aggregateId,
        AuditActorType.SYSTEM,
        "SYSTEM",
        actionType,
        AuditSeverity.INFO,
        occurredAt,
        notes);
  }

  public static AuditEvent integrationAction(
      String eventId,
      String aggregateType,
      String aggregateId,
      String integrationId,
      AuditActionType actionType,
      Instant occurredAt,
      Notes notes) {
    return of(
        eventId,
        aggregateType,
        aggregateId,
        AuditActorType.INTEGRATION,
        integrationId,
        actionType,
        AuditSeverity.INFO,
        occurredAt,
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
          "Il codice audit non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice audit può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  public String getEventId() {
    return eventId;
  }

  public String getAggregateType() {
    return aggregateType;
  }

  public String getAggregateId() {
    return aggregateId;
  }

  public AuditActorType getActorType() {
    return actorType;
  }

  public String getActorId() {
    return actorId;
  }

  public AuditActionType getActionType() {
    return actionType;
  }

  public AuditSeverity getSeverity() {
    return severity;
  }

  public Instant getOccurredAt() {
    return occurredAt;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isHumanAction() {
    return actorType.isHumanActor();
  }

  public boolean isSystemAction() {
    return actorType == AuditActorType.SYSTEM;
  }

  public boolean isIntegrationAction() {
    return actorType == AuditActorType.INTEGRATION;
  }

  public boolean isDataChange() {
    return actionType.isDataChange();
  }

  public boolean isSecuritySensitive() {
    return actionType.isSecuritySensitive();
  }

  public boolean hasFinancialImpact() {
    return actionType.hasFinancialImpact();
  }

  public boolean requiresReview() {
    return severity.requiresReview() || isSecuritySensitive();
  }

  public boolean hasNotes() {
    return notes.hasText();
  }

  public boolean isForAggregate(String aggregateType, String aggregateId) {
    return this.aggregateType.equals(
            validateCode(aggregateType, "Il tipo aggregato da verificare è obbligatorio."))
        && this.aggregateId.equals(
            validateCode(aggregateId, "L'id aggregato da verificare è obbligatorio."));
  }

  public boolean isSameAggregate(AuditEvent other) {
    if (other == null) {
      throw new IllegalArgumentException("L'evento audit da confrontare è obbligatorio.");
    }

    return aggregateType.equals(other.aggregateType) && aggregateId.equals(other.aggregateId);
  }

  public boolean isBeforeOrAtSameTime(AuditEvent other) {
    if (other == null) {
      throw new IllegalArgumentException("L'evento audit da confrontare è obbligatorio.");
    }

    return !occurredAt.isAfter(other.occurredAt);
  }

  public String formatSingleLine() {
    return eventId
        + " - "
        + aggregateType
        + ":"
        + aggregateId
        + " - "
        + actionType
        + " - "
        + actorType
        + ":"
        + actorId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AuditEvent that)) return false;
    return eventId.equals(that.eventId)
        && aggregateType.equals(that.aggregateType)
        && aggregateId.equals(that.aggregateId)
        && actorType == that.actorType
        && actorId.equals(that.actorId)
        && actionType == that.actionType
        && severity == that.severity
        && occurredAt.equals(that.occurredAt)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        eventId,
        aggregateType,
        aggregateId,
        actorType,
        actorId,
        actionType,
        severity,
        occurredAt,
        notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
