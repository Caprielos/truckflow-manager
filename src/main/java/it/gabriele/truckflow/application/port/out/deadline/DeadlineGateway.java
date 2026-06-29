package it.gabriele.truckflow.application.port.out.deadline;

import java.time.LocalDate;
import java.util.List;

/**
 * Porta in uscita verso il futuro compliance-deadline-service.
 *
 * <p>Il dominio principale usa questa interfaccia e non conosce il motore interno delle scadenze.
 * Oggi può essere collegata a un adapter in-process; domani a HTTP, gRPC o messaggistica.
 */
public interface DeadlineGateway {

  DeadlineGatewayResult evaluate(DeadlineGatewaySubject subject, LocalDate evaluationDate);

  List<DeadlineGatewayResult> evaluateBatch(
      List<DeadlineGatewaySubject> subjects, LocalDate evaluationDate);
}
