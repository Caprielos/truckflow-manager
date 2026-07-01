package it.gabriele.truckflow.infrastructure.memory.operational;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.operational.DispatcherRepository;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.dispatcher.Dispatcher;
import it.gabriele.truckflow.domain.operational.dispatcher.DispatcherId;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory implementation of the dispatcher repository port. */
public final class InMemoryDispatcherRepository implements DispatcherRepository {

  private final Map<DispatcherId, Dispatcher> dispatchersById = new HashMap<>();
  private final Map<OperationalCode, DispatcherId> idsByCode = new HashMap<>();
  private final Map<UserId, DispatcherId> idsByUserId = new HashMap<>();

  @Override
  public Dispatcher save(Dispatcher dispatcher) {
    UseCaseValidationException.requireNonNull(dispatcher, "dispatcher");

    ensureUniqueCode(dispatcher);
    ensureUniqueUserId(dispatcher);

    Dispatcher previousDispatcher = dispatchersById.put(dispatcher.id(), dispatcher);
    if (previousDispatcher != null) {
      removePreviousIndexes(previousDispatcher, dispatcher);
    }

    idsByCode.put(dispatcher.code(), dispatcher.id());
    idsByUserId.put(dispatcher.userId(), dispatcher.id());

    return dispatcher;
  }

  @Override
  public Optional<Dispatcher> findById(DispatcherId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(dispatchersById.get(id));
  }

  @Override
  public Optional<Dispatcher> findByCode(OperationalCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    DispatcherId id = idsByCode.get(code);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public Optional<Dispatcher> findByUserId(UserId userId) {
    UseCaseValidationException.requireNonNull(userId, "userId");
    DispatcherId id = idsByUserId.get(userId);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public boolean existsById(DispatcherId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return dispatchersById.containsKey(id);
  }

  @Override
  public boolean existsByCode(OperationalCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    return idsByCode.containsKey(code);
  }

  @Override
  public boolean existsByUserId(UserId userId) {
    UseCaseValidationException.requireNonNull(userId, "userId");
    return idsByUserId.containsKey(userId);
  }

  private void ensureUniqueCode(Dispatcher dispatcher) {
    DispatcherId existingId = idsByCode.get(dispatcher.code());
    if (existingId != null && !existingId.equals(dispatcher.id())) {
      throw new DuplicateResourceException("Dispatcher", dispatcher.code().value());
    }
  }

  private void ensureUniqueUserId(Dispatcher dispatcher) {
    DispatcherId existingId = idsByUserId.get(dispatcher.userId());
    if (existingId != null && !existingId.equals(dispatcher.id())) {
      throw new DuplicateResourceException("Dispatcher", dispatcher.userId().value());
    }
  }

  private void removePreviousIndexes(Dispatcher previousDispatcher, Dispatcher newDispatcher) {
    if (!previousDispatcher.code().equals(newDispatcher.code())) {
      idsByCode.remove(previousDispatcher.code());
    }

    if (!previousDispatcher.userId().equals(newDispatcher.userId())) {
      idsByUserId.remove(previousDispatcher.userId());
    }
  }
}
