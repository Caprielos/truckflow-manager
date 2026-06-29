package it.gabriele.truckflow.deadlineservice.application;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineSubject;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationEngine;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationPlan;
import it.gabriele.truckflow.deadlineservice.infrastructure.rulepack.ResourceDeadlineRulePackProvider;
import java.time.LocalDate;
import java.util.List;

/**
 * Facade minimale del futuro microservizio: espone un'API Java semplice, pronta per essere
 * collegata domani a REST, gRPC o messaggistica.
 */
public final class DeadlineServiceFacade {
  private final EvaluateDeadlineUseCase evaluateDeadlineUseCase;
  private final EvaluateDeadlineBatchUseCase evaluateDeadlineBatchUseCase;

  public DeadlineServiceFacade(
      EvaluateDeadlineUseCase evaluateDeadlineUseCase,
      EvaluateDeadlineBatchUseCase evaluateDeadlineBatchUseCase) {
    if (evaluateDeadlineUseCase == null) {
      throw new IllegalArgumentException("evaluateDeadlineUseCase è obbligatorio.");
    }
    if (evaluateDeadlineBatchUseCase == null) {
      throw new IllegalArgumentException("evaluateDeadlineBatchUseCase è obbligatorio.");
    }
    this.evaluateDeadlineUseCase = evaluateDeadlineUseCase;
    this.evaluateDeadlineBatchUseCase = evaluateDeadlineBatchUseCase;
  }

  public static DeadlineServiceFacade usingDefaultRulePack() {
    DefaultEvaluateDeadlineUseCase singleUseCase =
        new DefaultEvaluateDeadlineUseCase(
            ResourceDeadlineRulePackProvider.defaultResource(), new DeadlineEvaluationEngine());
    return new DeadlineServiceFacade(
        singleUseCase, new DefaultEvaluateDeadlineBatchUseCase(singleUseCase));
  }

  public DeadlineEvaluationPlan evaluate(DeadlineSubject subject, LocalDate evaluationDate) {
    return evaluateDeadlineUseCase.evaluate(new EvaluateDeadlineCommand(subject, evaluationDate));
  }

  public List<DeadlineEvaluationPlan> evaluateBatch(
      List<DeadlineSubject> subjects, LocalDate evaluationDate) {
    return evaluateDeadlineBatchUseCase.evaluateBatch(
        new EvaluateDeadlineBatchCommand(subjects, evaluationDate));
  }
}
