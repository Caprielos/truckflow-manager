package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.deadline.EnterpriseDeadline;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface EvaluateEnterpriseDeadlinesUseCase {

  Report handle(Command command);

  record Command(LocalDate today, Instant raisedAt) {}

  record Report(
      List<EnterpriseDeadline> attentionDeadlines,
      List<EnterpriseDeadline> blockingDeadlines,
      List<AlertEvent> generatedAlerts) {}
}
