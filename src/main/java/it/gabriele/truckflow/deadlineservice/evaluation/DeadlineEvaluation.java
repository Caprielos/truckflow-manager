package it.gabriele.truckflow.deadlineservice.evaluation;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineObjectRef;
import it.gabriele.truckflow.deadlineservice.domain.DeadlineRuleSourceType;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import java.time.LocalDate;
import java.util.Set;

/** Risultato di valutazione di una singola regola su un singolo elemento gestito. */
public record DeadlineEvaluation(
    DeadlineObjectRef objectRef,
    ManagedElementCode elementCode,
    DeadlineEvaluationStatus status,
    String sourceRuleId,
    Set<DeadlineRuleSourceType> sourceTypes,
    LocalDate nextDueDate,
    Long nextDueKm,
    boolean blocksOperation,
    String explanation) {

  public DeadlineEvaluation {
    if (objectRef == null) {
      throw new IllegalArgumentException("objectRef è obbligatorio.");
    }
    if (elementCode == null) {
      throw new IllegalArgumentException("elementCode è obbligatorio.");
    }
    if (status == null) {
      throw new IllegalArgumentException("status è obbligatorio.");
    }
    sourceRuleId = sourceRuleId == null ? "" : sourceRuleId.strip();
    sourceTypes = sourceTypes == null ? Set.of() : Set.copyOf(sourceTypes);
    explanation = explanation == null ? "" : explanation.strip();
  }

  public boolean preventsOperation() {
    return status == DeadlineEvaluationStatus.BLOCKING
        || (blocksOperation
            && (status == DeadlineEvaluationStatus.OVERDUE
                || status == DeadlineEvaluationStatus.DUE_NOW
                || status == DeadlineEvaluationStatus.MANUAL_REVIEW_REQUIRED
                || status == DeadlineEvaluationStatus.CONFIGURATION_MISSING));
  }
}
