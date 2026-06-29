/**
 * Porte in uscita verso repository scadenze e verso il futuro compliance-deadline-service.
 *
 * <p>Questo package mantiene separato il dominio principale dal motore delle scadenze: gli use case
 * applicativi usano {@code DeadlineGateway}, mentre l'implementazione concreta può essere
 * in-process, HTTP, gRPC o event-driven.
 *
 * <p>Questo file documenta il contenuto del package e non contiene logica applicativa.
 */
package it.gabriele.truckflow.application.port.out.deadline;
