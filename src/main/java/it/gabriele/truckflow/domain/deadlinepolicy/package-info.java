/**
 * Modello di dominio per scadenze legali e tecniche configurabili.
 *
 * <p>Questo package separa le scadenze imposte dalla normativa del paese configurato dalle scadenze
 * tecniche imposte dal costruttore del veicolo, del rimorchio o del componente.
 *
 * <p>Per modificare gli intervalli tecnici di un camion o di un rimorchio si aggiorna {@link
 * it.gabriele.truckflow.domain.deadlinepolicy.ConfigurableTechnicalDeadlineRuleBook}, senza
 * cambiare il calcolo delle scadenze.
 */
package it.gabriele.truckflow.domain.deadlinepolicy;
