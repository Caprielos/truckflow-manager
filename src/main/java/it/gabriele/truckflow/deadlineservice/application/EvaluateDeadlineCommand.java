package it.gabriele.truckflow.deadlineservice.application;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineSubject;
import java.time.LocalDate;

/** Comando applicativo per valutare un oggetto generico nel servizio scadenze. */
public record EvaluateDeadlineCommand(DeadlineSubject subject, LocalDate evaluationDate) {

  public EvaluateDeadlineCommand {
    if (subject == null) {
      throw new IllegalArgumentException("subject è obbligatorio.");
    }
    evaluationDate = evaluationDate == null ? LocalDate.now() : evaluationDate;
  }
}
