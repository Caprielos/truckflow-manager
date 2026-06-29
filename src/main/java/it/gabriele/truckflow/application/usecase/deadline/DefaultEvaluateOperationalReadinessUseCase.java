package it.gabriele.truckflow.application.usecase.deadline;

import it.gabriele.truckflow.application.port.in.EvaluateOperationalReadinessUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.EnterpriseDeadlineRepository;
import it.gabriele.truckflow.domain.alerting.AlertRules;
import it.gabriele.truckflow.domain.deadline.DeadlineRules;
import it.gabriele.truckflow.domain.deadline.EnterpriseDeadline;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Caso d'uso: verificare se una risorsa può essere usata operativamente. */
public final class DefaultEvaluateOperationalReadinessUseCase
    implements EvaluateOperationalReadinessUseCase {

  private final EnterpriseDeadlineRepository deadlineRepository;
  private final AlertEventRepository alertRepository;

  public DefaultEvaluateOperationalReadinessUseCase(
      EnterpriseDeadlineRepository deadlineRepository, AlertEventRepository alertRepository) {
    this.deadlineRepository =
        Objects.requireNonNull(deadlineRepository, "Il repository scadenze è obbligatorio.");
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public Report handle(Command command) {
    Objects.requireNonNull(command, "Il comando readiness è obbligatorio.");
    Objects.requireNonNull(command.ownerType(), "Il tipo proprietario è obbligatorio.");
    Objects.requireNonNull(command.today(), "La data controllo readiness è obbligatoria.");
    String ownerCode = normalize(command.ownerCode(), "Il codice proprietario è obbligatorio.");

    List<String> blockers = new ArrayList<>();
    List<String> warnings = new ArrayList<>();

    for (EnterpriseDeadline deadline : deadlineRepository.findAll()) {
      if (deadline.getOwnerType() != command.ownerType()
          || !deadline.getOwnerCode().equals(ownerCode)) {
        continue;
      }

      if (DeadlineRules.blocksOperations(deadline, command.today())) {
        blockers.add("Scadenza bloccante: " + deadline.getDeadlineCode());
      } else if (DeadlineRules.requiresAttention(deadline, command.today())) {
        warnings.add("Scadenza da controllare: " + deadline.getDeadlineCode());
      }
    }

    alertRepository.findAll().stream()
        .filter(
            alert ->
                alert.getSourceCode().equals(ownerCode)
                    || deadlineRepository.findAll().stream()
                        .anyMatch(
                            deadline ->
                                deadline.getOwnerType() == command.ownerType()
                                    && deadline.getOwnerCode().equals(ownerCode)
                                    && deadline.getDeadlineCode().equals(alert.getSourceCode())))
        .filter(AlertRules::requiresImmediateAttention)
        .forEach(alert -> warnings.add("Alert attivo: " + alert.getAlertCode()));

    return new Report(blockers.isEmpty(), List.copyOf(blockers), List.copyOf(warnings));
  }

  private static String normalize(String code, String message) {
    if (code == null || code.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return code.trim().toUpperCase();
  }
}
