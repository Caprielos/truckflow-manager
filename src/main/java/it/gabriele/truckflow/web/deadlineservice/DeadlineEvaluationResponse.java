package it.gabriele.truckflow.web.deadlineservice;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineRuleSourceType;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluation;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationStatus;
import java.time.LocalDate;
import java.util.Set;

/** Risultato REST di una singola valutazione scadenza. */
public record DeadlineEvaluationResponse(
    ManagedElementCode elementCode,
    DeadlineEvaluationStatus status,
    String sourceRuleId,
    Set<DeadlineRuleSourceType> sourceTypes,
    LocalDate nextDueDate,
    Long nextDueKm,
    boolean blocksOperation,
    boolean preventsOperation,
    String explanation) {

  static DeadlineEvaluationResponse fromDomain(DeadlineEvaluation evaluation) {
    return new DeadlineEvaluationResponse(
        evaluation.elementCode(),
        evaluation.status(),
        evaluation.sourceRuleId(),
        evaluation.sourceTypes(),
        evaluation.nextDueDate(),
        evaluation.nextDueKm(),
        evaluation.blocksOperation(),
        evaluation.preventsOperation(),
        evaluation.explanation());
  }
}
