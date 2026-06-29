package it.gabriele.truckflow.deadlineservice.application;

import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationPlan;
import java.util.List;

/** Implementazione applicativa stateless della valutazione batch. */
public final class DefaultEvaluateDeadlineBatchUseCase implements EvaluateDeadlineBatchUseCase {
  private final EvaluateDeadlineUseCase evaluateDeadlineUseCase;

  public DefaultEvaluateDeadlineBatchUseCase(EvaluateDeadlineUseCase evaluateDeadlineUseCase) {
    if (evaluateDeadlineUseCase == null) {
      throw new IllegalArgumentException("evaluateDeadlineUseCase è obbligatorio.");
    }
    this.evaluateDeadlineUseCase = evaluateDeadlineUseCase;
  }

  @Override
  public List<DeadlineEvaluationPlan> evaluateBatch(EvaluateDeadlineBatchCommand command) {
    if (command == null) {
      throw new IllegalArgumentException("command è obbligatorio.");
    }
    return command.subjects().stream()
        .map(
            subject ->
                evaluateDeadlineUseCase.evaluate(
                    new EvaluateDeadlineCommand(subject, command.evaluationDate())))
        .toList();
  }
}
