package it.gabriele.truckflow.application.port.out.operational;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperator;
import it.gabriele.truckflow.domain.operational.warehouse.WarehouseOperatorId;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.Optional;

/** Outbound repository port used by warehouseoperator operational role use cases. */
public interface WarehouseOperatorRepository extends RepositoryPort {

  WarehouseOperator save(WarehouseOperator warehouseOperator);

  Optional<WarehouseOperator> findById(WarehouseOperatorId id);

  Optional<WarehouseOperator> findByCode(OperationalCode code);

  Optional<WarehouseOperator> findByUserId(UserId userId);

  boolean existsById(WarehouseOperatorId id);

  boolean existsByCode(OperationalCode code);

  boolean existsByUserId(UserId userId);
}
