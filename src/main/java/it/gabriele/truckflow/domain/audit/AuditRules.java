package it.gabriele.truckflow.domain.audit;

/** Regole di dominio per audit trail. */
public final class AuditRules {

  private AuditRules() {}

  public static boolean canAppendEvent(AuditTrail trail, AuditEvent newEvent) {
    validateTrail(trail);
    validateEvent(newEvent);

    return trail.getLatestEvent().isSameAggregate(newEvent)
        && trail.getLatestEvent().isBeforeOrAtSameTime(newEvent)
        && !trail.containsEventId(newEvent.getEventId());
  }

  public static boolean requiresReview(AuditEvent event) {
    validateEvent(event);

    return event.requiresReview();
  }

  public static boolean requiresReview(AuditTrail trail) {
    validateTrail(trail);

    return trail.hasReviewRequiredEvents();
  }

  public static boolean containsSecuritySensitiveEvents(AuditTrail trail) {
    validateTrail(trail);

    return trail.hasSecuritySensitiveEvents();
  }

  public static boolean containsFinancialImpactEvents(AuditTrail trail) {
    validateTrail(trail);

    return trail.hasFinancialImpactEvents();
  }

  public static boolean isChronological(AuditTrail trail) {
    validateTrail(trail);

    for (int i = 1; i < trail.getEvents().size(); i++) {
      AuditEvent previous = trail.getEvents().get(i - 1);
      AuditEvent current = trail.getEvents().get(i);

      if (!previous.isBeforeOrAtSameTime(current)) {
        return false;
      }
    }

    return true;
  }

  private static void validateTrail(AuditTrail trail) {
    if (trail == null) {
      throw new IllegalArgumentException("L'audit trail è obbligatorio.");
    }
  }

  private static void validateEvent(AuditEvent event) {
    if (event == null) {
      throw new IllegalArgumentException("L'evento audit è obbligatorio.");
    }
  }
}
