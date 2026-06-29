package it.gabriele.truckflow.deadlineservice.application;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineSubject;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePack;

/**
 * Porta applicativa che fornisce il rule pack attivo per tenant, Paese e oggetto valutato.
 *
 * <p>Oggi può leggere il file YAML locale. Domani potrà leggere database, config server o object
 * storage senza cambiare gli use case.
 */
public interface DeadlineRulePackProvider {

  DeadlineRulePack activeRulePackFor(DeadlineSubject subject);
}
