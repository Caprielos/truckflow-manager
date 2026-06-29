package it.gabriele.truckflow.deadlineservice.infrastructure.rulepack;

import it.gabriele.truckflow.deadlineservice.application.DeadlineRulePackProvider;
import it.gabriele.truckflow.deadlineservice.domain.DeadlineSubject;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePack;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePackResourceLoader;

/**
 * Adapter semplice che fornisce il rule pack leggendo la risorsa YAML inclusa nell'applicazione.
 */
public final class ResourceDeadlineRulePackProvider implements DeadlineRulePackProvider {
  private final DeadlineRulePack rulePack;

  public ResourceDeadlineRulePackProvider(DeadlineRulePack rulePack) {
    if (rulePack == null) {
      throw new IllegalArgumentException("rulePack è obbligatorio.");
    }
    this.rulePack = rulePack;
  }

  public static ResourceDeadlineRulePackProvider defaultResource() {
    return new ResourceDeadlineRulePackProvider(DeadlineRulePackResourceLoader.loadDefault());
  }

  @Override
  public DeadlineRulePack activeRulePackFor(DeadlineSubject subject) {
    if (subject == null) {
      throw new IllegalArgumentException("subject è obbligatorio.");
    }
    return rulePack;
  }
}
