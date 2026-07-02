package it.gabriele.truckflow.infrastructure.config.spring;

import it.gabriele.truckflow.application.port.in.operational.ActivateDispatcherUseCase;
import it.gabriele.truckflow.application.port.in.operational.ActivateDriverUseCase;
import it.gabriele.truckflow.application.port.in.operational.ActivateManagerUseCase;
import it.gabriele.truckflow.application.port.in.operational.ActivateMechanicUseCase;
import it.gabriele.truckflow.application.port.in.operational.ActivateWarehouseOperatorUseCase;
import it.gabriele.truckflow.application.port.in.operational.FindDispatcherUseCase;
import it.gabriele.truckflow.application.port.in.operational.FindDriverUseCase;
import it.gabriele.truckflow.application.port.in.operational.FindManagerUseCase;
import it.gabriele.truckflow.application.port.in.operational.FindMechanicUseCase;
import it.gabriele.truckflow.application.port.in.operational.FindWarehouseOperatorUseCase;
import it.gabriele.truckflow.application.port.in.operational.MarkNotEligibleDispatcherUseCase;
import it.gabriele.truckflow.application.port.in.operational.MarkNotEligibleDriverUseCase;
import it.gabriele.truckflow.application.port.in.operational.MarkNotEligibleManagerUseCase;
import it.gabriele.truckflow.application.port.in.operational.MarkNotEligibleMechanicUseCase;
import it.gabriele.truckflow.application.port.in.operational.MarkNotEligibleWarehouseOperatorUseCase;
import it.gabriele.truckflow.application.port.in.operational.RegisterDispatcherUseCase;
import it.gabriele.truckflow.application.port.in.operational.RegisterDriverUseCase;
import it.gabriele.truckflow.application.port.in.operational.RegisterManagerUseCase;
import it.gabriele.truckflow.application.port.in.operational.RegisterMechanicUseCase;
import it.gabriele.truckflow.application.port.in.operational.RegisterWarehouseOperatorUseCase;
import it.gabriele.truckflow.application.port.in.operational.SuspendDispatcherUseCase;
import it.gabriele.truckflow.application.port.in.operational.SuspendDriverUseCase;
import it.gabriele.truckflow.application.port.in.operational.SuspendManagerUseCase;
import it.gabriele.truckflow.application.port.in.operational.SuspendMechanicUseCase;
import it.gabriele.truckflow.application.port.in.operational.SuspendWarehouseOperatorUseCase;
import it.gabriele.truckflow.application.port.out.operational.DispatcherRepository;
import it.gabriele.truckflow.application.port.out.operational.DriverRepository;
import it.gabriele.truckflow.application.port.out.operational.ManagerRepository;
import it.gabriele.truckflow.application.port.out.operational.MechanicRepository;
import it.gabriele.truckflow.application.port.out.operational.WarehouseOperatorRepository;
import it.gabriele.truckflow.application.usecase.operational.ActivateDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.ActivateDriverService;
import it.gabriele.truckflow.application.usecase.operational.ActivateManagerService;
import it.gabriele.truckflow.application.usecase.operational.ActivateMechanicService;
import it.gabriele.truckflow.application.usecase.operational.ActivateWarehouseOperatorService;
import it.gabriele.truckflow.application.usecase.operational.FindDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.FindDriverService;
import it.gabriele.truckflow.application.usecase.operational.FindManagerService;
import it.gabriele.truckflow.application.usecase.operational.FindMechanicService;
import it.gabriele.truckflow.application.usecase.operational.FindWarehouseOperatorService;
import it.gabriele.truckflow.application.usecase.operational.MarkNotEligibleDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.MarkNotEligibleDriverService;
import it.gabriele.truckflow.application.usecase.operational.MarkNotEligibleManagerService;
import it.gabriele.truckflow.application.usecase.operational.MarkNotEligibleMechanicService;
import it.gabriele.truckflow.application.usecase.operational.MarkNotEligibleWarehouseOperatorService;
import it.gabriele.truckflow.application.usecase.operational.RegisterDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.RegisterDriverService;
import it.gabriele.truckflow.application.usecase.operational.RegisterManagerService;
import it.gabriele.truckflow.application.usecase.operational.RegisterMechanicService;
import it.gabriele.truckflow.application.usecase.operational.RegisterWarehouseOperatorService;
import it.gabriele.truckflow.application.usecase.operational.SuspendDispatcherService;
import it.gabriele.truckflow.application.usecase.operational.SuspendDriverService;
import it.gabriele.truckflow.application.usecase.operational.SuspendManagerService;
import it.gabriele.truckflow.application.usecase.operational.SuspendMechanicService;
import it.gabriele.truckflow.application.usecase.operational.SuspendWarehouseOperatorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Spring bean wiring for operational-role application use cases. */
@Configuration
public class OperationalUseCaseSpringConfiguration {

  @Bean
  public RegisterDriverUseCase registerDriverUseCase(DriverRepository driverRepository) {
    return new RegisterDriverService(driverRepository);
  }

  @Bean
  public FindDriverUseCase findDriverUseCase(DriverRepository driverRepository) {
    return new FindDriverService(driverRepository);
  }

  @Bean
  public ActivateDriverUseCase activateDriverUseCase(DriverRepository driverRepository) {
    return new ActivateDriverService(driverRepository);
  }

  @Bean
  public SuspendDriverUseCase suspendDriverUseCase(DriverRepository driverRepository) {
    return new SuspendDriverService(driverRepository);
  }

  @Bean
  public MarkNotEligibleDriverUseCase markNotEligibleDriverUseCase(
      DriverRepository driverRepository) {
    return new MarkNotEligibleDriverService(driverRepository);
  }

  @Bean
  public RegisterMechanicUseCase registerMechanicUseCase(MechanicRepository mechanicRepository) {
    return new RegisterMechanicService(mechanicRepository);
  }

  @Bean
  public FindMechanicUseCase findMechanicUseCase(MechanicRepository mechanicRepository) {
    return new FindMechanicService(mechanicRepository);
  }

  @Bean
  public ActivateMechanicUseCase activateMechanicUseCase(MechanicRepository mechanicRepository) {
    return new ActivateMechanicService(mechanicRepository);
  }

  @Bean
  public SuspendMechanicUseCase suspendMechanicUseCase(MechanicRepository mechanicRepository) {
    return new SuspendMechanicService(mechanicRepository);
  }

  @Bean
  public MarkNotEligibleMechanicUseCase markNotEligibleMechanicUseCase(
      MechanicRepository mechanicRepository) {
    return new MarkNotEligibleMechanicService(mechanicRepository);
  }

  @Bean
  public RegisterWarehouseOperatorUseCase registerWarehouseOperatorUseCase(
      WarehouseOperatorRepository warehouseOperatorRepository) {
    return new RegisterWarehouseOperatorService(warehouseOperatorRepository);
  }

  @Bean
  public FindWarehouseOperatorUseCase findWarehouseOperatorUseCase(
      WarehouseOperatorRepository warehouseOperatorRepository) {
    return new FindWarehouseOperatorService(warehouseOperatorRepository);
  }

  @Bean
  public ActivateWarehouseOperatorUseCase activateWarehouseOperatorUseCase(
      WarehouseOperatorRepository warehouseOperatorRepository) {
    return new ActivateWarehouseOperatorService(warehouseOperatorRepository);
  }

  @Bean
  public SuspendWarehouseOperatorUseCase suspendWarehouseOperatorUseCase(
      WarehouseOperatorRepository warehouseOperatorRepository) {
    return new SuspendWarehouseOperatorService(warehouseOperatorRepository);
  }

  @Bean
  public MarkNotEligibleWarehouseOperatorUseCase markNotEligibleWarehouseOperatorUseCase(
      WarehouseOperatorRepository warehouseOperatorRepository) {
    return new MarkNotEligibleWarehouseOperatorService(warehouseOperatorRepository);
  }

  @Bean
  public RegisterDispatcherUseCase registerDispatcherUseCase(
      DispatcherRepository dispatcherRepository) {
    return new RegisterDispatcherService(dispatcherRepository);
  }

  @Bean
  public FindDispatcherUseCase findDispatcherUseCase(DispatcherRepository dispatcherRepository) {
    return new FindDispatcherService(dispatcherRepository);
  }

  @Bean
  public ActivateDispatcherUseCase activateDispatcherUseCase(
      DispatcherRepository dispatcherRepository) {
    return new ActivateDispatcherService(dispatcherRepository);
  }

  @Bean
  public SuspendDispatcherUseCase suspendDispatcherUseCase(
      DispatcherRepository dispatcherRepository) {
    return new SuspendDispatcherService(dispatcherRepository);
  }

  @Bean
  public MarkNotEligibleDispatcherUseCase markNotEligibleDispatcherUseCase(
      DispatcherRepository dispatcherRepository) {
    return new MarkNotEligibleDispatcherService(dispatcherRepository);
  }

  @Bean
  public RegisterManagerUseCase registerManagerUseCase(ManagerRepository managerRepository) {
    return new RegisterManagerService(managerRepository);
  }

  @Bean
  public FindManagerUseCase findManagerUseCase(ManagerRepository managerRepository) {
    return new FindManagerService(managerRepository);
  }

  @Bean
  public ActivateManagerUseCase activateManagerUseCase(ManagerRepository managerRepository) {
    return new ActivateManagerService(managerRepository);
  }

  @Bean
  public SuspendManagerUseCase suspendManagerUseCase(ManagerRepository managerRepository) {
    return new SuspendManagerService(managerRepository);
  }

  @Bean
  public MarkNotEligibleManagerUseCase markNotEligibleManagerUseCase(
      ManagerRepository managerRepository) {
    return new MarkNotEligibleManagerService(managerRepository);
  }
}
