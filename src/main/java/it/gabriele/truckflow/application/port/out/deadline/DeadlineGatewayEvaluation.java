package it.gabriele.truckflow.application.port.out.deadline;

import java.time.LocalDate;
import java.util.Set;

/** Esito di una singola regola o di un singolo elemento valutato dal servizio scadenze. */
public record DeadlineGatewayEvaluation(
    String elementCode,
    DeadlineGatewayStatus status,
    String sourceRuleId,
    Set<String> sourceTypes,
    LocalDate nextDueDate,
    Long nextDueKm,
    boolean preventsOperation,
    String explanation) {

  public DeadlineGatewayEvaluation {
    elementCode = requireText(elementCode, "elementCode");
    if (status == null) {
      throw new IllegalArgumentException("status è obbligatorio.");
    }
    sourceRuleId = sourceRuleId == null ? "" : sourceRuleId.strip();
    sourceTypes = sourceTypes == null ? Set.of() : Set.copyOf(sourceTypes);
    explanation = explanation == null ? "" : explanation.strip();
  }

  private static String requireText(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " è obbligatorio.");
    }
    return value.strip();
  }
}
