package it.gabriele.truckflow.application.port.out.deadline;

import java.time.LocalDate;
import java.util.List;

/** Risultato aggregato restituito al dominio principale dal servizio scadenze. */
public record DeadlineGatewayResult(
    DeadlineGatewaySubject subject,
    LocalDate evaluatedAt,
    DeadlineGatewayStatus overallStatus,
    boolean canOperate,
    List<DeadlineGatewayEvaluation> evaluations) {

  public DeadlineGatewayResult {
    if (subject == null) {
      throw new IllegalArgumentException("subject è obbligatorio.");
    }
    if (evaluatedAt == null) {
      throw new IllegalArgumentException("evaluatedAt è obbligatorio.");
    }
    if (overallStatus == null) {
      throw new IllegalArgumentException("overallStatus è obbligatorio.");
    }
    evaluations = evaluations == null ? List.of() : List.copyOf(evaluations);
  }

  public boolean hasBlockingIssue() {
    return !canOperate || overallStatus == DeadlineGatewayStatus.BLOCKING;
  }

  public boolean hasConfigurationMissing() {
    return evaluations.stream()
        .anyMatch(evaluation -> evaluation.status() == DeadlineGatewayStatus.CONFIGURATION_MISSING);
  }

  public boolean hasOverdueIssue() {
    return evaluations.stream()
        .anyMatch(
            evaluation ->
                evaluation.status() == DeadlineGatewayStatus.OVERDUE
                    || evaluation.status() == DeadlineGatewayStatus.DUE_NOW
                    || evaluation.status() == DeadlineGatewayStatus.BLOCKING);
  }
}
