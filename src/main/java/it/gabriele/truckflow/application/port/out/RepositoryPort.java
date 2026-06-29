package it.gabriele.truckflow.application.port.out;

import it.gabriele.truckflow.application.common.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Porta di persistenza generica. Il domain resta puro: non conosce database, file o memoria.
 */
public interface RepositoryPort<T> {

    Optional<T> findById(String id);

    void save(T aggregate);

    List<T> findAll();

    default boolean existsById(String id) {
        return findById(id).isPresent();
    }

    default T getRequired(String id, String resourceName) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException(resourceName, id));
    }
}
