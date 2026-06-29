package it.gabriele.truckflow.deadlineservice.rulepack;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineRuleSourceType;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import java.util.Set;

/** Regola configurabile, oppure slot vuoto, letto dal file unico deadline-rule-pack.yml. */
public record DeadlineRulePackRule(
    String ruleId,
    ManagedElementCode elementCode,
    Set<DeadlineRuleSourceType> sourceTypes,
    DeadlineRuleSlotStatus status,
    boolean fillableFromUi,
    String description) {

  public DeadlineRulePackRule {
    ruleId = requireText(ruleId, "ruleId");
    if (elementCode == null) {
      throw new IllegalArgumentException("elementCode è obbligatorio.");
    }
    if (sourceTypes == null || sourceTypes.isEmpty()) {
      throw new IllegalArgumentException("sourceTypes deve contenere almeno una fonte.");
    }
    sourceTypes = Set.copyOf(sourceTypes);
    if (status == null) {
      throw new IllegalArgumentException("status è obbligatorio.");
    }
    description = description == null ? "" : description.strip();
  }

  public boolean isEmptySlot() {
    return status == DeadlineRuleSlotStatus.EMPTY_SLOT;
  }

  public boolean isActive() {
    return status == DeadlineRuleSlotStatus.ACTIVE;
  }

  private static String requireText(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " è obbligatorio.");
    }
    return value.strip();
  }
}
