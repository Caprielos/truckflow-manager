package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.EvaluateEnterpriseDeadlinesUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.EnterpriseDeadlineRepository;
import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.alerting.AlertSourceType;
import it.gabriele.truckflow.domain.alerting.AlertType;
import it.gabriele.truckflow.domain.deadline.DeadlineRules;
import it.gabriele.truckflow.domain.deadline.DeadlineStatus;
import it.gabriele.truckflow.domain.deadline.EnterpriseDeadline;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Caso d'uso: controllare lo scadenziario e generare alert operativi. */
public final class DefaultEvaluateEnterpriseDeadlinesUseCase
    implements EvaluateEnterpriseDeadlinesUseCase {

  private final EnterpriseDeadlineRepository deadlineRepository;
  private final AlertEventRepository alertRepository;

  public DefaultEvaluateEnterpriseDeadlinesUseCase(
      EnterpriseDeadlineRepository deadlineRepository, AlertEventRepository alertRepository) {
    this.deadlineRepository =
        Objects.requireNonNull(deadlineRepository, "Il repository scadenze è obbligatorio.");
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public Report handle(Command command) {
    Objects.requireNonNull(command, "Il comando controllo scadenze è obbligatorio.");
    Objects.requireNonNull(command.today(), "La data controllo scadenze è obbligatoria.");
    Objects.requireNonNull(command.raisedAt(), "La data creazione alert è obbligatoria.");

    List<EnterpriseDeadline> attentionDeadlines = new ArrayList<>();
    List<EnterpriseDeadline> blockingDeadlines = new ArrayList<>();
    List<AlertEvent> generatedAlerts = new ArrayList<>();

    for (EnterpriseDeadline deadline : deadlineRepository.findAll()) {
      DeadlineStatus calculatedStatus = DeadlineRules.calculateStatus(deadline, command.today());

      if (!calculatedStatus.requiresAttention()) {
        continue;
      }

      attentionDeadlines.add(deadline);

      if (DeadlineRules.blocksOperations(deadline, command.today())) {
        blockingDeadlines.add(deadline);
      }

      AlertEvent alert = buildAlert(deadline, calculatedStatus, command);
      if (!alertRepository.existsById(alert.getAlertCode())) {
        alertRepository.save(alert);
        generatedAlerts.add(alert);
      }
    }

    return new Report(
        List.copyOf(attentionDeadlines),
        List.copyOf(blockingDeadlines),
        List.copyOf(generatedAlerts));
  }

  private AlertEvent buildAlert(
      EnterpriseDeadline deadline, DeadlineStatus calculatedStatus, Command command) {
    AlertType alertType =
        calculatedStatus == DeadlineStatus.OVERDUE
            ? AlertType.DEADLINE_OVERDUE
            : AlertType.DEADLINE_DUE_SOON;
    String title =
        calculatedStatus == DeadlineStatus.OVERDUE ? "Scadenza superata" : "Scadenza vicina";
    String message =
        "Scadenza "
            + deadline.getType()
            + " per "
            + deadline.getOwnerType()
            + " "
            + deadline.getOwnerCode()
            + " con data "
            + deadline.getDueDate();

    return EnterpriseAlertFactory.open(
        "DL",
        deadline.getDeadlineCode(),
        calculatedStatus.name(),
        alertType,
        EnterpriseAlertFactory.fromDeadlineSeverity(deadline.getSeverity()),
        AlertSourceType.DEADLINE,
        title,
        message,
        command.raisedAt(),
        Notes.of("Alert generato automaticamente da controllo scadenze."));
  }
}
