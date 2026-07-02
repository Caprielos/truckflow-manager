package it.gabriele.truckflow.infrastructure.config.spring;

import it.gabriele.truckflow.application.port.out.cargo.CargoUnitRepository;
import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.application.port.out.documents.DocumentRepository;
import it.gabriele.truckflow.application.port.out.locations.LocationRepository;
import it.gabriele.truckflow.application.port.out.operational.DispatcherRepository;
import it.gabriele.truckflow.application.port.out.operational.DriverRepository;
import it.gabriele.truckflow.application.port.out.operational.ManagerRepository;
import it.gabriele.truckflow.application.port.out.operational.MechanicRepository;
import it.gabriele.truckflow.application.port.out.operational.WarehouseOperatorRepository;
import it.gabriele.truckflow.application.port.out.shipments.ShipmentRepository;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleCombinationRepository;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleUnitRepository;
import it.gabriele.truckflow.infrastructure.memory.cargo.InMemoryCargoUnitRepository;
import it.gabriele.truckflow.infrastructure.memory.compliance.InMemoryComplianceRequirementRepository;
import it.gabriele.truckflow.infrastructure.memory.documents.InMemoryDocumentRepository;
import it.gabriele.truckflow.infrastructure.memory.locations.InMemoryLocationRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryDispatcherRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryDriverRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryManagerRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryMechanicRepository;
import it.gabriele.truckflow.infrastructure.memory.operational.InMemoryWarehouseOperatorRepository;
import it.gabriele.truckflow.infrastructure.memory.shipments.InMemoryShipmentRepository;
import it.gabriele.truckflow.infrastructure.memory.vehicles.InMemoryVehicleCombinationRepository;
import it.gabriele.truckflow.infrastructure.memory.vehicles.InMemoryVehicleUnitRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/** Wires the current in-memory repository adapters under the dedicated memory profile. */
@Configuration
@Profile(SpringProfileNames.MEMORY)
public class InMemoryRepositorySpringConfiguration {

  @Bean
  public LocationRepository locationRepository() {
    return new InMemoryLocationRepository();
  }

  @Bean
  public CargoUnitRepository cargoUnitRepository() {
    return new InMemoryCargoUnitRepository();
  }

  @Bean
  public ShipmentRepository shipmentRepository() {
    return new InMemoryShipmentRepository();
  }

  @Bean
  public DocumentRepository documentRepository() {
    return new InMemoryDocumentRepository();
  }

  @Bean
  public VehicleUnitRepository vehicleUnitRepository() {
    return new InMemoryVehicleUnitRepository();
  }

  @Bean
  public VehicleCombinationRepository vehicleCombinationRepository() {
    return new InMemoryVehicleCombinationRepository();
  }

  @Bean
  public DriverRepository driverRepository() {
    return new InMemoryDriverRepository();
  }

  @Bean
  public MechanicRepository mechanicRepository() {
    return new InMemoryMechanicRepository();
  }

  @Bean
  public WarehouseOperatorRepository warehouseOperatorRepository() {
    return new InMemoryWarehouseOperatorRepository();
  }

  @Bean
  public DispatcherRepository dispatcherRepository() {
    return new InMemoryDispatcherRepository();
  }

  @Bean
  public ManagerRepository managerRepository() {
    return new InMemoryManagerRepository();
  }

  @Bean
  public ComplianceRequirementRepository complianceRequirementRepository() {
    return new InMemoryComplianceRequirementRepository();
  }
}
