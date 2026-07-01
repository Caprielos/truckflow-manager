package it.gabriele.truckflow.application.port.out;

import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.port.out.operational.DispatcherRepository;
import it.gabriele.truckflow.application.port.out.operational.DriverRepository;
import it.gabriele.truckflow.application.port.out.operational.ManagerRepository;
import it.gabriele.truckflow.application.port.out.operational.MechanicRepository;
import it.gabriele.truckflow.application.port.out.operational.WarehouseOperatorRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class ApplicationOperationalRepositoryPortTest {

  @Test
  void operationalRepositoryPortsExtendTheBaseRepositoryPortContract() {
    List<Class<?>> repositoryPorts =
        List.of(
            DriverRepository.class,
            MechanicRepository.class,
            WarehouseOperatorRepository.class,
            DispatcherRepository.class,
            ManagerRepository.class);

    assertTrue(
        repositoryPorts.stream().allMatch(RepositoryPort.class::isAssignableFrom),
        () -> "All operational repository ports must extend RepositoryPort: " + repositoryPorts);
  }
}
