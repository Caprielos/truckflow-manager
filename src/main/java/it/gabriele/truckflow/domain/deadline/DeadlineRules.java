package it.gabriele.truckflow.domain.deadline;

import java.time.LocalDate;

/** Regole di dominio per lo scadenziario enterprise. */
public final class DeadlineRules {

  private DeadlineRules() {}

  public static DeadlineStatus calculateStatus(EnterpriseDeadline deadline, LocalDate today) {
    validateDeadline(deadline);
    validateToday(today);

    if (deadline.getStatus().isTerminal()) {
      return deadline.getStatus();
    }

    if (isOverdue(deadline, today)) {
      return DeadlineStatus.OVERDUE;
    }

    if (isDueSoon(deadline, today)) {
      return DeadlineStatus.DUE_SOON;
    }

    return DeadlineStatus.PLANNED;
  }

  public static boolean isDueSoon(EnterpriseDeadline deadline, LocalDate today) {
    validateDeadline(deadline);
    validateToday(today);

    return deadline.getWarningDate() != null
        && !today.isBefore(deadline.getWarningDate())
        && !today.isAfter(deadline.getDueDate());
  }

  public static boolean isOverdue(EnterpriseDeadline deadline, LocalDate today) {
    validateDeadline(deadline);
    validateToday(today);

    return today.isAfter(deadline.getDueDate());
  }

  public static boolean requiresAttention(EnterpriseDeadline deadline, LocalDate today) {
    validateDeadline(deadline);

    return calculateStatus(deadline, today).requiresAttention();
  }

  public static boolean blocksOperations(EnterpriseDeadline deadline, LocalDate today) {
    validateDeadline(deadline);

    return deadline.blocksOperationsWhenExpired() && isOverdue(deadline, today);
  }

  public static boolean canBeCompleted(EnterpriseDeadline deadline) {
    validateDeadline(deadline);

    return !deadline.getStatus().isTerminal();
  }

  public static boolean canBeWaived(EnterpriseDeadline deadline) {
    validateDeadline(deadline);

    return deadline.getStatus() == DeadlineStatus.DUE_SOON
        || deadline.getStatus() == DeadlineStatus.OVERDUE;
  }

  private static void validateDeadline(EnterpriseDeadline deadline) {
    if (deadline == null) {
      throw new IllegalArgumentException("La scadenza è obbligatoria.");
    }
  }

  private static void validateToday(LocalDate today) {
    if (today == null) {
      throw new IllegalArgumentException("La data di riferimento è obbligatoria.");
    }
  }
}
