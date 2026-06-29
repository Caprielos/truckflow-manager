package it.gabriele.truckflow.deadlineservice.evaluation;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineSubject;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/** Risultato aggregato della valutazione scadenze di un oggetto generico. */
public record DeadlineEvaluationPlan(
    DeadlineSubject subject,
    LocalDate evaluatedAt,
    DeadlineEvaluationStatus overallStatus,
    boolean canOperate,
    List<DeadlineEvaluation> evaluations) {

  public DeadlineEvaluationPlan {
    if (subject == null) {
      throw new IllegalArgumentException("subject è obbligatorio.");
    }
    if (evaluatedAt == null) {
      throw new IllegalArgumentException("evaluatedAt è obbligatorio.");
    }
    if (overallStatus == null) {
      throw new IllegalArgumentException("overallStatus è obbligatorio.");
    }
    evaluations = evaluations == null ? List.of() : List.copyOf(evaluations);
  }

  public static DeadlineEvaluationPlan from(
      DeadlineSubject subject, LocalDate evaluatedAt, List<DeadlineEvaluation> evaluations) {
    List<DeadlineEvaluation> safeEvaluations =
        evaluations == null ? List.of() : List.copyOf(evaluations);
    boolean canOperate = safeEvaluations.stream().noneMatch(DeadlineEvaluation::preventsOperation);
    DeadlineEvaluationStatus overallStatus =
        safeEvaluations.stream()
            .map(DeadlineEvaluation::status)
            .max(Comparator.comparingInt(DeadlineEvaluationPlan::severity))
            .orElse(DeadlineEvaluationStatus.NOT_APPLICABLE);
    if (!canOperate && severity(overallStatus) < severity(DeadlineEvaluationStatus.BLOCKING)) {
      overallStatus = DeadlineEvaluationStatus.BLOCKING;
    }
    return new DeadlineEvaluationPlan(
        subject, evaluatedAt, overallStatus, canOperate, safeEvaluations);
  }

  private static int severity(DeadlineEvaluationStatus status) {
    return switch (status) {
      case NOT_APPLICABLE -> 0;
      case OK -> 1;
      case CONFIGURATION_MISSING -> 2;
      case SUSPENDED -> 3;
      case DUE_SOON -> 4;
      case MANUAL_REVIEW_REQUIRED -> 5;
      case DUE_NOW -> 6;
      case OVERDUE -> 7;
      case BLOCKING -> 8;
    };
  }
}
