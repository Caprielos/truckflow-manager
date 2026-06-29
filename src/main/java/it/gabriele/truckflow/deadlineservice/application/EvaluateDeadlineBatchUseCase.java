package it.gabriele.truckflow.deadlineservice.application;

import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationPlan;
import java.util.List;

/** Porta di ingresso applicativa per valutare più oggetti in un'unica richiesta. */
public interface EvaluateDeadlineBatchUseCase {

  List<DeadlineEvaluationPlan> evaluateBatch(EvaluateDeadlineBatchCommand command);
}
