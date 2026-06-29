package it.gabriele.truckflow.deadlineservice.application;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineSubject;
import java.time.LocalDate;
import java.util.List;

/** Comando applicativo per valutare più oggetti nello stesso momento logico. */
public record EvaluateDeadlineBatchCommand(
    List<DeadlineSubject> subjects, LocalDate evaluationDate) {

  public EvaluateDeadlineBatchCommand {
    subjects = subjects == null ? List.of() : List.copyOf(subjects);
    evaluationDate = evaluationDate == null ? LocalDate.now() : evaluationDate;
  }
}
