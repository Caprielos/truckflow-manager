package it.gabriele.truckflow.deadlineservice.application;

import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationPlan;

/** Porta di ingresso applicativa per valutare le scadenze di un singolo oggetto. */
public interface EvaluateDeadlineUseCase {

  DeadlineEvaluationPlan evaluate(EvaluateDeadlineCommand command);
}
