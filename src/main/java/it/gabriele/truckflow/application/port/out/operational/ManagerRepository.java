package it.gabriele.truckflow.application.port.out.operational;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.manager.Manager;
import it.gabriele.truckflow.domain.operational.manager.ManagerId;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.Optional;

/** Outbound repository port used by manager operational role use cases. */
public interface ManagerRepository extends RepositoryPort {

  Manager save(Manager manager);

  Optional<Manager> findById(ManagerId id);

  Optional<Manager> findByCode(OperationalCode code);

  Optional<Manager> findByUserId(UserId userId);

  boolean existsById(ManagerId id);

  boolean existsByCode(OperationalCode code);

  boolean existsByUserId(UserId userId);
}
