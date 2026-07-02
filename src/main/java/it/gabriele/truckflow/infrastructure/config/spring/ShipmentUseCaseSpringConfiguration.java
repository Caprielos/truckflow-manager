package it.gabriele.truckflow.infrastructure.config.spring;

import it.gabriele.truckflow.application.port.in.shipments.AddShipmentItemUseCase;
import it.gabriele.truckflow.application.port.in.shipments.AddShipmentLegUseCase;
import it.gabriele.truckflow.application.port.in.shipments.CancelShipmentUseCase;
import it.gabriele.truckflow.application.port.in.shipments.ConfirmShipmentUseCase;
import it.gabriele.truckflow.application.port.in.shipments.CreateShipmentUseCase;
import it.gabriele.truckflow.application.port.in.shipments.FindShipmentUseCase;
import it.gabriele.truckflow.application.port.out.cargo.CargoUnitRepository;
import it.gabriele.truckflow.application.port.out.locations.LocationRepository;
import it.gabriele.truckflow.application.port.out.shipments.ShipmentRepository;
import it.gabriele.truckflow.application.usecase.shipments.AddShipmentItemService;
import it.gabriele.truckflow.application.usecase.shipments.AddShipmentLegService;
import it.gabriele.truckflow.application.usecase.shipments.CancelShipmentService;
import it.gabriele.truckflow.application.usecase.shipments.ConfirmShipmentService;
import it.gabriele.truckflow.application.usecase.shipments.CreateShipmentService;
import it.gabriele.truckflow.application.usecase.shipments.FindShipmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Spring bean wiring for shipment application use cases. */
@Configuration
public class ShipmentUseCaseSpringConfiguration {

  @Bean
  public CreateShipmentUseCase createShipmentUseCase(ShipmentRepository shipmentRepository) {
    return new CreateShipmentService(shipmentRepository);
  }

  @Bean
  public FindShipmentUseCase findShipmentUseCase(ShipmentRepository shipmentRepository) {
    return new FindShipmentService(shipmentRepository);
  }

  @Bean
  public AddShipmentItemUseCase addShipmentItemUseCase(
      ShipmentRepository shipmentRepository, CargoUnitRepository cargoUnitRepository) {
    return new AddShipmentItemService(shipmentRepository, cargoUnitRepository);
  }

  @Bean
  public AddShipmentLegUseCase addShipmentLegUseCase(
      ShipmentRepository shipmentRepository, LocationRepository locationRepository) {
    return new AddShipmentLegService(shipmentRepository, locationRepository);
  }

  @Bean
  public ConfirmShipmentUseCase confirmShipmentUseCase(ShipmentRepository shipmentRepository) {
    return new ConfirmShipmentService(shipmentRepository);
  }

  @Bean
  public CancelShipmentUseCase cancelShipmentUseCase(ShipmentRepository shipmentRepository) {
    return new CancelShipmentService(shipmentRepository);
  }
}
