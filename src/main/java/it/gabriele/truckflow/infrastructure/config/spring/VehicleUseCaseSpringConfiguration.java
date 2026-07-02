package it.gabriele.truckflow.infrastructure.config.spring;

import it.gabriele.truckflow.application.port.in.vehicles.ActivateVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.DismissVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.FindVehicleCombinationUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.FindVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.MarkVehicleUnitOutOfServiceUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.RegisterVehicleCombinationUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.RegisterVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.SuspendVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleCombinationRepository;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleUnitRepository;
import it.gabriele.truckflow.application.usecase.vehicles.ActivateVehicleUnitService;
import it.gabriele.truckflow.application.usecase.vehicles.DismissVehicleUnitService;
import it.gabriele.truckflow.application.usecase.vehicles.FindVehicleCombinationService;
import it.gabriele.truckflow.application.usecase.vehicles.FindVehicleUnitService;
import it.gabriele.truckflow.application.usecase.vehicles.MarkVehicleUnitOutOfServiceService;
import it.gabriele.truckflow.application.usecase.vehicles.RegisterVehicleCombinationService;
import it.gabriele.truckflow.application.usecase.vehicles.RegisterVehicleUnitService;
import it.gabriele.truckflow.application.usecase.vehicles.SuspendVehicleUnitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Spring bean wiring for vehicle application use cases. */
@Configuration
public class VehicleUseCaseSpringConfiguration {

  @Bean
  public RegisterVehicleUnitUseCase registerVehicleUnitUseCase(
      VehicleUnitRepository vehicleUnitRepository) {
    return new RegisterVehicleUnitService(vehicleUnitRepository);
  }

  @Bean
  public FindVehicleUnitUseCase findVehicleUnitUseCase(
      VehicleUnitRepository vehicleUnitRepository) {
    return new FindVehicleUnitService(vehicleUnitRepository);
  }

  @Bean
  public ActivateVehicleUnitUseCase activateVehicleUnitUseCase(
      VehicleUnitRepository vehicleUnitRepository) {
    return new ActivateVehicleUnitService(vehicleUnitRepository);
  }

  @Bean
  public SuspendVehicleUnitUseCase suspendVehicleUnitUseCase(
      VehicleUnitRepository vehicleUnitRepository) {
    return new SuspendVehicleUnitService(vehicleUnitRepository);
  }

  @Bean
  public MarkVehicleUnitOutOfServiceUseCase markVehicleUnitOutOfServiceUseCase(
      VehicleUnitRepository vehicleUnitRepository) {
    return new MarkVehicleUnitOutOfServiceService(vehicleUnitRepository);
  }

  @Bean
  public DismissVehicleUnitUseCase dismissVehicleUnitUseCase(
      VehicleUnitRepository vehicleUnitRepository) {
    return new DismissVehicleUnitService(vehicleUnitRepository);
  }

  @Bean
  public RegisterVehicleCombinationUseCase registerVehicleCombinationUseCase(
      VehicleUnitRepository vehicleUnitRepository,
      VehicleCombinationRepository vehicleCombinationRepository) {
    return new RegisterVehicleCombinationService(
        vehicleUnitRepository, vehicleCombinationRepository);
  }

  @Bean
  public FindVehicleCombinationUseCase findVehicleCombinationUseCase(
      VehicleCombinationRepository vehicleCombinationRepository) {
    return new FindVehicleCombinationService(vehicleCombinationRepository);
  }
}
