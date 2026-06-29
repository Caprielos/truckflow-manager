package it.gabriele.truckflow.domain.alerting;

/** Regole di dominio per alert e priorità operative. */
public final class AlertRules {

  private AlertRules() {}

  public static boolean canBeAcknowledged(AlertEvent alert) {
    validateAlert(alert);

    return alert.getStatus() == AlertStatus.OPEN;
  }

  public static boolean canBeClosed(AlertEvent alert) {
    validateAlert(alert);

    return alert.getStatus() == AlertStatus.OPEN || alert.getStatus() == AlertStatus.ACKNOWLEDGED;
  }

  public static boolean requiresImmediateAttention(AlertEvent alert) {
    validateAlert(alert);

    return alert.isActive() && alert.getSeverity().requiresAttention();
  }

  public static boolean requiresEscalation(AlertEvent alert) {
    validateAlert(alert);

    return alert.isActive() && alert.requiresEscalation();
  }

  private static void validateAlert(AlertEvent alert) {
    if (alert == null) {
      throw new IllegalArgumentException("L'alert è obbligatorio.");
    }
  }
}
