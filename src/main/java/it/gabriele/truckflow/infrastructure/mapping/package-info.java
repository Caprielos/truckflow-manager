/**
 * Mapping contracts and blueprints for translating between domain models and future technical
 * persistence models.
 *
 * <p>Mappers must not create business rules. Their responsibility is translating already-valid
 * domain objects to technical representations and rebuilding domain objects through public domain
 * APIs. Punto 7D adds mapping blueprints only: no JPA entity, database schema, Spring Data
 * repository or concrete persistence adapter is introduced here.
 */
package it.gabriele.truckflow.infrastructure.mapping;
