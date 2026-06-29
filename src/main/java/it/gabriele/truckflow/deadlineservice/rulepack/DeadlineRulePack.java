package it.gabriele.truckflow.deadlineservice.rulepack;

import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import java.util.List;

/** File logico versionato che contiene regole e slot configurabili del servizio scadenze. */
public record DeadlineRulePack(
    String id,
    String version,
    DeadlineRulePackStatus status,
    String defaultCountry,
    String tenantId,
    List<DeadlineRulePackRule> rules) {

  public DeadlineRulePack {
    id = requireText(id, "id");
    version = requireText(version, "version");
    if (status == null) {
      throw new IllegalArgumentException("status è obbligatorio.");
    }
    defaultCountry = requireText(defaultCountry, "defaultCountry");
    tenantId = requireText(tenantId, "tenantId");
    rules = rules == null ? List.of() : List.copyOf(rules);
    if (rules.isEmpty()) {
      throw new IllegalArgumentException("Il rule pack deve contenere almeno una regola o slot.");
    }
  }

  public List<DeadlineRulePackRule> rulesForElement(ManagedElementCode elementCode) {
    return rules.stream().filter(rule -> rule.elementCode() == elementCode).toList();
  }

  public List<DeadlineRulePackRule> emptySlots() {
    return rules.stream().filter(DeadlineRulePackRule::isEmptySlot).toList();
  }

  public boolean hasRuleFor(ManagedElementCode elementCode) {
    return rules.stream().anyMatch(rule -> rule.elementCode() == elementCode);
  }

  private static String requireText(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " è obbligatorio.");
    }
    return value.strip();
  }
}
