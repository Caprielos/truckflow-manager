/**
 * Repository adapter conventions for future real implementations of application outbound ports.
 *
 * <p>Concrete repository adapters introduced here must implement port.out contracts from the
 * application layer. They must not replace the in-memory adapters unless a wiring configuration
 * explicitly chooses them.
 */
package it.gabriele.truckflow.infrastructure.repository;
