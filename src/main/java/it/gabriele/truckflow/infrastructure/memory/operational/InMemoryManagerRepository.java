package it.gabriele.truckflow.infrastructure.memory.operational;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.operational.ManagerRepository;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.manager.Manager;
import it.gabriele.truckflow.domain.operational.manager.ManagerId;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory implementation of the manager repository port. */
public final class InMemoryManagerRepository implements ManagerRepository {

  private final Map<ManagerId, Manager> managersById = new HashMap<>();
  private final Map<OperationalCode, ManagerId> idsByCode = new HashMap<>();
  private final Map<UserId, ManagerId> idsByUserId = new HashMap<>();

  @Override
  public Manager save(Manager manager) {
    UseCaseValidationException.requireNonNull(manager, "manager");

    ensureUniqueCode(manager);
    ensureUniqueUserId(manager);

    Manager previousManager = managersById.put(manager.id(), manager);
    if (previousManager != null) {
      removePreviousIndexes(previousManager, manager);
    }

    idsByCode.put(manager.code(), manager.id());
    idsByUserId.put(manager.userId(), manager.id());

    return manager;
  }

  @Override
  public Optional<Manager> findById(ManagerId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(managersById.get(id));
  }

  @Override
  public Optional<Manager> findByCode(OperationalCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    ManagerId id = idsByCode.get(code);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public Optional<Manager> findByUserId(UserId userId) {
    UseCaseValidationException.requireNonNull(userId, "userId");
    ManagerId id = idsByUserId.get(userId);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public boolean existsById(ManagerId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return managersById.containsKey(id);
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

  private void ensureUniqueCode(Manager manager) {
    ManagerId existingId = idsByCode.get(manager.code());
    if (existingId != null && !existingId.equals(manager.id())) {
      throw new DuplicateResourceException("Manager", manager.code().value());
    }
  }

  private void ensureUniqueUserId(Manager manager) {
    ManagerId existingId = idsByUserId.get(manager.userId());
    if (existingId != null && !existingId.equals(manager.id())) {
      throw new DuplicateResourceException("Manager", manager.userId().value());
    }
  }

  private void removePreviousIndexes(Manager previousManager, Manager newManager) {
    if (!previousManager.code().equals(newManager.code())) {
      idsByCode.remove(previousManager.code());
    }

    if (!previousManager.userId().equals(newManager.userId())) {
      idsByUserId.remove(previousManager.userId());
    }
  }
}
