/**
 * Spring-based infrastructure wiring.
 *
 * <p>This package is the only place where Spring is allowed to compose current application use
 * cases and repository adapters during Punto 7C. It must not introduce web delivery, persistence
 * adapters, security, or business rules.
 */
package it.gabriele.truckflow.infrastructure.config.spring;
