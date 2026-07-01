package it.gabriele.truckflow.infrastructure.mapping;

/**
 * Generic infrastructure mapping contract between a domain model and a persistence model.
 *
 * @param <D> domain model type
 * @param <P> persistence model type
 */
public interface PersistenceMapper<D, P> {

  /** Converts a domain object to a persistence representation. */
  P toPersistence(D domainModel);

  /** Rebuilds a domain object from a persistence representation. */
  D toDomain(P persistenceModel);
}
