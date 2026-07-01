package it.gabriele.truckflow.application.port.out.operational;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.mechanic.Mechanic;
import it.gabriele.truckflow.domain.operational.mechanic.MechanicId;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.Optional;

/** Outbound repository port used by mechanic operational role use cases. */
public interface MechanicRepository extends RepositoryPort {

  Mechanic save(Mechanic mechanic);

  Optional<Mechanic> findById(MechanicId id);

  Optional<Mechanic> findByCode(OperationalCode code);

  Optional<Mechanic> findByUserId(UserId userId);

  boolean existsById(MechanicId id);

  boolean existsByCode(OperationalCode code);

  boolean existsByUserId(UserId userId);
}
