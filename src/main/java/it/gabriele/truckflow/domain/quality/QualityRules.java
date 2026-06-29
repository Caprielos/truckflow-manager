package it.gabriele.truckflow.domain.quality;

/** Regole di dominio per qualità servizio e azioni correttive. */
public final class QualityRules {

  private QualityRules() {}

  public static boolean canAssignCorrectiveAction(QualityEvent event) {
    validateEvent(event);

    return event.isActive() && event.getCorrectiveAction() == null;
  }

  public static boolean canBeClosed(QualityEvent event) {
    validateEvent(event);

    return event.isActive()
        && (!event.requiresManagementReview()
            || (event.getCorrectiveAction() != null && event.getCorrectiveAction().isCompleted()));
  }

  private static void validateEvent(QualityEvent event) {
    if (event == null) {
      throw new IllegalArgumentException("L'evento qualità è obbligatorio.");
    }
  }
}
