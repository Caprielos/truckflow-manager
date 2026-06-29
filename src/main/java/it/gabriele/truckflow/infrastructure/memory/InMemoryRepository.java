package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * Repository in memoria usato per test di scenario, demo e primi use case. Non è un database: i
 * dati vivono solo finché l'applicazione resta in esecuzione.
 */
public class InMemoryRepository<T> implements RepositoryPort<T> {

  private final ConcurrentMap<String, T> storage = new ConcurrentHashMap<>();
  private final Function<T, String> idExtractor;

  protected InMemoryRepository(Function<T, String> idExtractor) {
    this.idExtractor = Objects.requireNonNull(idExtractor, "L'estrattore id è obbligatorio.");
  }

  @Override
  public Optional<T> findById(String id) {
    if (id == null || id.trim().isEmpty()) {
      return Optional.empty();
    }
    return Optional.ofNullable(storage.get(normalizeId(id)));
  }

  @Override
  public void save(T aggregate) {
    Objects.requireNonNull(aggregate, "L'aggregato da salvare è obbligatorio.");
    String id = normalizeId(idExtractor.apply(aggregate));
    if (id.isEmpty()) {
      throw new IllegalArgumentException("L'id dell'aggregato non può essere vuoto.");
    }
    storage.put(id, aggregate);
  }

  @Override
  public List<T> findAll() {
    return List.copyOf(new ArrayList<>(storage.values()));
  }

  public void deleteById(String id) {
    if (id != null) {
      storage.remove(normalizeId(id));
    }
  }

  public void clear() {
    storage.clear();
  }

  protected static String normalizeId(String id) {
    return id == null ? "" : id.trim().toUpperCase();
  }
}
