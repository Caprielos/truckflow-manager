package it.gabriele.truckflow.domain.sla;

import java.time.Duration;
import java.time.Instant;

/** Regole di dominio per SLA, violazioni e penali. */
public final class SlaRules {

  private SlaRules() {}

  public static boolean canBeActivated(ServiceLevelAgreement agreement) {
    validateAgreement(agreement);

    return agreement.getStatus() == SlaStatus.DRAFT || agreement.getStatus() == SlaStatus.SUSPENDED;
  }

  public static boolean isLate(Instant plannedAt, Instant actualAt, int allowedMinutes) {
    if (plannedAt == null || actualAt == null) {
      throw new IllegalArgumentException("Le date pianificata e reale sono obbligatorie.");
    }

    if (allowedMinutes < 0) {
      throw new IllegalArgumentException("I minuti ammessi non possono essere negativi.");
    }

    return Duration.between(plannedAt, actualAt).toMinutes() > allowedMinutes;
  }

  public static boolean canBeWaived(SlaViolation violation) {
    validateViolation(violation);

    return !violation.isWaived() && violation.hasPenalty();
  }

  private static void validateAgreement(ServiceLevelAgreement agreement) {
    if (agreement == null) {
      throw new IllegalArgumentException("Lo SLA è obbligatorio.");
    }
  }

  private static void validateViolation(SlaViolation violation) {
    if (violation == null) {
      throw new IllegalArgumentException("La violazione SLA è obbligatoria.");
    }
  }
}
