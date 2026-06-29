package it.gabriele.truckflow.deadlineservice.rulepack;

import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCatalog;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import java.util.List;

/** Controlla che nessun elemento del catalogo rimanga senza almeno uno slot nel rule pack. */
public final class DeadlineRulePackCoverageValidator {
  private DeadlineRulePackCoverageValidator() {}

  public static List<ManagedElementCode> missingManagedElements(DeadlineRulePack rulePack) {
    return ManagedElementCatalog.all().stream()
        .map(definition -> definition.code())
        .filter(code -> !rulePack.hasRuleFor(code))
        .toList();
  }

  public static void requireFullCoverage(DeadlineRulePack rulePack) {
    List<ManagedElementCode> missing = missingManagedElements(rulePack);
    if (!missing.isEmpty()) {
      throw new IllegalStateException("Rule pack senza slot per gli elementi: " + missing);
    }
  }
}
