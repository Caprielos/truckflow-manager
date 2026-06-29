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
    String description,
    DeadlineRuleIntervalType intervalType,
    Integer intervalDays,
    Long intervalKm,
    int warningDaysBefore,
    long warningKmBefore,
    boolean blocksOperation) {

  public DeadlineRulePackRule(
      String ruleId,
      ManagedElementCode elementCode,
      Set<DeadlineRuleSourceType> sourceTypes,
      DeadlineRuleSlotStatus status,
      boolean fillableFromUi,
      String description) {
    this(
        ruleId,
        elementCode,
        sourceTypes,
        status,
        fillableFromUi,
        description,
        DeadlineRuleIntervalType.NOT_CONFIGURED,
        null,
        null,
        0,
        0,
        false);
  }

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
    intervalType = intervalType == null ? DeadlineRuleIntervalType.NOT_CONFIGURED : intervalType;
    if (warningDaysBefore < 0) {
      throw new IllegalArgumentException("warningDaysBefore non può essere negativo.");
    }
    if (warningKmBefore < 0) {
      throw new IllegalArgumentException("warningKmBefore non può essere negativo.");
    }
    if (intervalDays != null && intervalDays <= 0) {
      throw new IllegalArgumentException("intervalDays deve essere positivo quando configurato.");
    }
    if (intervalKm != null && intervalKm <= 0) {
      throw new IllegalArgumentException("intervalKm deve essere positivo quando configurato.");
    }
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
