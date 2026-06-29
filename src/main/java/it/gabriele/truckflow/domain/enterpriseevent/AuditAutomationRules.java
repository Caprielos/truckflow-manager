package it.gabriele.truckflow.domain.enterpriseevent;

/** Regole per audit trail automatico e escalation. */
public final class AuditAutomationRules {

  private AuditAutomationRules() {}

  public static AuditDecision decide(DomainEventEnvelope event) {
    if (event == null) {
      throw new IllegalArgumentException("L'evento domain è obbligatorio.");
    }
    return switch (event.eventType()) {
      case ROAD_INSPECTION_FAILED,
          SLA_VIOLATED,
          DEADLINE_EXPIRED,
          VEHICLE_BLOCKED,
          DRIVER_BLOCKED ->
          AuditDecision.AUDIT_AND_ESCALATE;
      case DOCUMENT_VERSIONED, COST_ALLOCATED, CLAIM_OPENED, POD_COLLECTED ->
          AuditDecision.AUDIT_FULL;
      case ORDER_CREATED,
          SHIPMENT_PLANNED,
          MISSION_DISPATCHED,
          MISSION_DELAYED,
          ALERT_RAISED,
          INTEGRATION_FAILED ->
          AuditDecision.AUDIT_LIGHT;
    };
  }

  public static boolean requiresReason(DomainEventEnvelope event) {
    if (event == null) {
      throw new IllegalArgumentException("L'evento domain è obbligatorio.");
    }
    AuditDecision decision = decide(event);
    return decision == AuditDecision.AUDIT_FULL || decision == AuditDecision.AUDIT_AND_ESCALATE;
  }
}
