package it.gabriele.truckflow.web.deadlineservice;

import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePack;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePackStatus;

/** Informazioni sintetiche sul rule pack attualmente caricato dal deadline-service. */
public record DeadlineRulePackSummaryResponse(
    String id,
    String version,
    DeadlineRulePackStatus status,
    String defaultCountry,
    String tenantId,
    int rulesCount,
    int emptySlotsCount) {

  static DeadlineRulePackSummaryResponse fromDomain(DeadlineRulePack rulePack) {
    return new DeadlineRulePackSummaryResponse(
        rulePack.id(),
        rulePack.version(),
        rulePack.status(),
        rulePack.defaultCountry(),
        rulePack.tenantId(),
        rulePack.rules().size(),
        rulePack.emptySlots().size());
  }
}
