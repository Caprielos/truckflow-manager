package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.domain.alerting.AlertEvent;
import java.util.List;
import java.util.Optional;

/** Risultato comune per controlli applicativi enterprise. */
public record EnterpriseValidationResult(
    boolean passed, boolean blocked, List<String> messages, Optional<AlertEvent> alert) {

  public EnterpriseValidationResult {
    messages = messages == null ? List.of() : List.copyOf(messages);
    alert = alert == null ? Optional.empty() : alert;
  }

  public static EnterpriseValidationResult passed(String message) {
    return new EnterpriseValidationResult(true, false, List.of(message), Optional.empty());
  }

  public static EnterpriseValidationResult failed(
      boolean blocked, List<String> messages, Optional<AlertEvent> alert) {
    return new EnterpriseValidationResult(false, blocked, messages, alert);
  }
}
