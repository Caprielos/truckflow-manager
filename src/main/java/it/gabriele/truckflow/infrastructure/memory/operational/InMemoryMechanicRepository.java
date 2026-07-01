package it.gabriele.truckflow.infrastructure.memory.operational;

import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.out.operational.MechanicRepository;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.mechanic.Mechanic;
import it.gabriele.truckflow.domain.operational.mechanic.MechanicId;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** In-memory implementation of the mechanic repository port. */
public final class InMemoryMechanicRepository implements MechanicRepository {

  private final Map<MechanicId, Mechanic> mechanicsById = new HashMap<>();
  private final Map<OperationalCode, MechanicId> idsByCode = new HashMap<>();
  private final Map<UserId, MechanicId> idsByUserId = new HashMap<>();

  @Override
  public Mechanic save(Mechanic mechanic) {
    UseCaseValidationException.requireNonNull(mechanic, "mechanic");

    ensureUniqueCode(mechanic);
    ensureUniqueUserId(mechanic);

    Mechanic previousMechanic = mechanicsById.put(mechanic.id(), mechanic);
    if (previousMechanic != null) {
      removePreviousIndexes(previousMechanic, mechanic);
    }

    idsByCode.put(mechanic.code(), mechanic.id());
    idsByUserId.put(mechanic.userId(), mechanic.id());

    return mechanic;
  }

  @Override
  public Optional<Mechanic> findById(MechanicId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return Optional.ofNullable(mechanicsById.get(id));
  }

  @Override
  public Optional<Mechanic> findByCode(OperationalCode code) {
    UseCaseValidationException.requireNonNull(code, "code");
    MechanicId id = idsByCode.get(code);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public Optional<Mechanic> findByUserId(UserId userId) {
    UseCaseValidationException.requireNonNull(userId, "userId");
    MechanicId id = idsByUserId.get(userId);
    return id == null ? Optional.empty() : findById(id);
  }

  @Override
  public boolean existsById(MechanicId id) {
    UseCaseValidationException.requireNonNull(id, "id");
    return mechanicsById.containsKey(id);
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

  private void ensureUniqueCode(Mechanic mechanic) {
    MechanicId existingId = idsByCode.get(mechanic.code());
    if (existingId != null && !existingId.equals(mechanic.id())) {
      throw new DuplicateResourceException("Mechanic", mechanic.code().value());
    }
  }

  private void ensureUniqueUserId(Mechanic mechanic) {
    MechanicId existingId = idsByUserId.get(mechanic.userId());
    if (existingId != null && !existingId.equals(mechanic.id())) {
      throw new DuplicateResourceException("Mechanic", mechanic.userId().value());
    }
  }

  private void removePreviousIndexes(Mechanic previousMechanic, Mechanic newMechanic) {
    if (!previousMechanic.code().equals(newMechanic.code())) {
      idsByCode.remove(previousMechanic.code());
    }

    if (!previousMechanic.userId().equals(newMechanic.userId())) {
      idsByUserId.remove(previousMechanic.userId());
    }
  }
}
