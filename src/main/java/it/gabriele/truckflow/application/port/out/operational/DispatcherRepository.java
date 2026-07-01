package it.gabriele.truckflow.application.port.out.operational;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.dispatcher.Dispatcher;
import it.gabriele.truckflow.domain.operational.dispatcher.DispatcherId;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.Optional;

/** Outbound repository port used by dispatcher operational role use cases. */
public interface DispatcherRepository extends RepositoryPort {

  Dispatcher save(Dispatcher dispatcher);

  Optional<Dispatcher> findById(DispatcherId id);

  Optional<Dispatcher> findByCode(OperationalCode code);

  Optional<Dispatcher> findByUserId(UserId userId);

  boolean existsById(DispatcherId id);

  boolean existsByCode(OperationalCode code);

  boolean existsByUserId(UserId userId);
}
