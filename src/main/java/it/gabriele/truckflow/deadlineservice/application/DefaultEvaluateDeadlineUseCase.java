package it.gabriele.truckflow.deadlineservice.application;

import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationEngine;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationPlan;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePack;

/** Implementazione applicativa stateless della valutazione scadenze di un singolo oggetto. */
public final class DefaultEvaluateDeadlineUseCase implements EvaluateDeadlineUseCase {
  private final DeadlineRulePackProvider rulePackProvider;
  private final DeadlineEvaluationEngine evaluationEngine;

  public DefaultEvaluateDeadlineUseCase(
      DeadlineRulePackProvider rulePackProvider, DeadlineEvaluationEngine evaluationEngine) {
    if (rulePackProvider == null) {
      throw new IllegalArgumentException("rulePackProvider è obbligatorio.");
    }
    if (evaluationEngine == null) {
      throw new IllegalArgumentException("evaluationEngine è obbligatorio.");
    }
    this.rulePackProvider = rulePackProvider;
    this.evaluationEngine = evaluationEngine;
  }

  @Override
  public DeadlineEvaluationPlan evaluate(EvaluateDeadlineCommand command) {
    if (command == null) {
      throw new IllegalArgumentException("command è obbligatorio.");
    }
    DeadlineRulePack rulePack = rulePackProvider.activeRulePackFor(command.subject());
    return evaluationEngine.evaluate(command.subject(), rulePack, command.evaluationDate());
  }
}
