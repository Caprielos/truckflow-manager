package it.gabriele.truckflow.infrastructure.memory.operational;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.operational.WarehouseOperatorRepository;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperator;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperatorId;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory implementation of the warehouseoperator repository port. */
public final class InMemoryWarehouseOperatorRepository implements WarehouseOperatorRepository {

  private final Map<WarehouseOperatorId, WarehouseOperator> warehouseOperatorsById =
      new HashMap<>();
  private final Map<OperationalCode, WarehouseOperatorId> idsByCode = new HashMap<>();
  private final Map<UserId, WarehouseOperatorId> idsByUserId = new HashMap<>();

  @Override
  public WarehouseOperator save(WarehouseOperator warehouseOperator) {
    UseCaseValidationException.requireNonNull(warehouseOperator, "warehouseOperator");

    ensureUniqueCode(warehouseOperator);
    ensureUniqueUserId(warehouseOperator);

    WarehouseOperator previousWarehouseOperator =
        warehouseOperatorsById.put(warehouseOperator.id(), warehouseOperator);
    if (previousWarehouseOperator != null) {
      removePreviousIndexes(previousWarehouseOperator, warehouseOperator);
    }

    idsByCode.put(warehouseOperator.code(), warehouseOperator.id());
    idsByUserId.put(warehouseOperator.userId(), warehouseOperator.id());

    return warehouseOperator;
  }

  @Override
  public Optional<WarehouseOperator> findById(WarehouseOperatorId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(warehouseOperatorsById.get(id));
  }

  @Override
  public Optional<WarehouseOperator> findByCode(OperationalCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    WarehouseOperatorId id = idsByCode.get(code);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public Optional<WarehouseOperator> findByUserId(UserId userId) {
    UseCaseValidationException.requireNonNull(userId, "userId");
    WarehouseOperatorId id = idsByUserId.get(userId);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public boolean existsById(WarehouseOperatorId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return warehouseOperatorsById.containsKey(id);
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

  private void ensureUniqueCode(WarehouseOperator warehouseOperator) {
    WarehouseOperatorId existingId = idsByCode.get(warehouseOperator.code());
    if (existingId != null && !existingId.equals(warehouseOperator.id())) {
      throw new DuplicateResourceException("WarehouseOperator", warehouseOperator.code().value());
    }
  }

  private void ensureUniqueUserId(WarehouseOperator warehouseOperator) {
    WarehouseOperatorId existingId = idsByUserId.get(warehouseOperator.userId());
    if (existingId != null && !existingId.equals(warehouseOperator.id())) {
      throw new DuplicateResourceException("WarehouseOperator", warehouseOperator.userId().value());
    }
  }

  private void removePreviousIndexes(
      WarehouseOperator previousWarehouseOperator, WarehouseOperator newWarehouseOperator) {
    if (!previousWarehouseOperator.code().equals(newWarehouseOperator.code())) {
      idsByCode.remove(previousWarehouseOperator.code());
    }

    if (!previousWarehouseOperator.userId().equals(newWarehouseOperator.userId())) {
      idsByUserId.remove(previousWarehouseOperator.userId());
    }
  }
}
