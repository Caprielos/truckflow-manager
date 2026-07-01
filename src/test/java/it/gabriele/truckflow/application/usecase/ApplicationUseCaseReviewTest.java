package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.port.in.cargo.FindCargoUnitUseCase;
import it.gabriele.truckflow.application.port.in.cargo.RegisterCargoUnitUseCase;
import it.gabriele.truckflow.application.port.in.documents.ActivateDocumentUseCase;
import it.gabriele.truckflow.application.port.in.documents.ArchiveDocumentUseCase;
import it.gabriele.truckflow.application.port.in.documents.FindDocumentUseCase;
import it.gabriele.truckflow.application.port.in.documents.RegisterDocumentUseCase;
import it.gabriele.truckflow.application.port.in.locations.FindLocationUseCase;
import it.gabriele.truckflow.application.port.in.locations.RegisterLocationUseCase;
import it.gabriele.truckflow.application.port.in.shipments.AddShipmentItemUseCase;
import it.gabriele.truckflow.application.port.in.shipments.AddShipmentLegUseCase;
import it.gabriele.truckflow.application.port.in.shipments.CancelShipmentUseCase;
import it.gabriele.truckflow.application.port.in.shipments.ConfirmShipmentUseCase;
import it.gabriele.truckflow.application.port.in.shipments.CreateShipmentUseCase;
import it.gabriele.truckflow.application.port.in.shipments.FindShipmentUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.ActivateVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.DismissVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.FindVehicleCombinationUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.FindVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.MarkVehicleUnitOutOfServiceUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.RegisterVehicleCombinationUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.RegisterVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.SuspendVehicleUnitUseCase;
import it.gabriele.truckflow.application.result.cargo.CargoUnitResult;
import it.gabriele.truckflow.application.result.documents.DocumentResult;
import it.gabriele.truckflow.application.result.locations.LocationResult;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;
import it.gabriele.truckflow.application.result.vehicles.VehicleCombinationResult;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;
import it.gabriele.truckflow.application.usecase.cargo.FindCargoUnitService;
import it.gabriele.truckflow.application.usecase.cargo.RegisterCargoUnitService;
import it.gabriele.truckflow.application.usecase.documents.ActivateDocumentService;
import it.gabriele.truckflow.application.usecase.documents.ArchiveDocumentService;
import it.gabriele.truckflow.application.usecase.documents.FindDocumentService;
import it.gabriele.truckflow.application.usecase.documents.RegisterDocumentService;
import it.gabriele.truckflow.application.usecase.locations.FindLocationService;
import it.gabriele.truckflow.application.usecase.locations.RegisterLocationService;
import it.gabriele.truckflow.application.usecase.shipments.AddShipmentItemService;
import it.gabriele.truckflow.application.usecase.shipments.AddShipmentLegService;
import it.gabriele.truckflow.application.usecase.shipments.CancelShipmentService;
import it.gabriele.truckflow.application.usecase.shipments.ConfirmShipmentService;
import it.gabriele.truckflow.application.usecase.shipments.CreateShipmentService;
import it.gabriele.truckflow.application.usecase.shipments.FindShipmentService;
import it.gabriele.truckflow.application.usecase.vehicles.ActivateVehicleUnitService;
import it.gabriele.truckflow.application.usecase.vehicles.DismissVehicleUnitService;
import it.gabriele.truckflow.application.usecase.vehicles.FindVehicleCombinationService;
import it.gabriele.truckflow.application.usecase.vehicles.FindVehicleUnitService;
import it.gabriele.truckflow.application.usecase.vehicles.MarkVehicleUnitOutOfServiceService;
import it.gabriele.truckflow.application.usecase.vehicles.RegisterVehicleCombinationService;
import it.gabriele.truckflow.application.usecase.vehicles.RegisterVehicleUnitService;
import it.gabriele.truckflow.application.usecase.vehicles.SuspendVehicleUnitService;
import java.util.List;
import org.junit.jupiter.api.Test;

class ApplicationUseCaseReviewTest {

  @Test
  void currentUseCasePortsExtendTheBaseUseCaseContract() {
    List<Class<?>> useCasePorts =
        List.of(
            RegisterLocationUseCase.class,
            FindLocationUseCase.class,
            RegisterCargoUnitUseCase.class,
            FindCargoUnitUseCase.class,
            CreateShipmentUseCase.class,
            FindShipmentUseCase.class,
            AddShipmentItemUseCase.class,
            AddShipmentLegUseCase.class,
            ConfirmShipmentUseCase.class,
            CancelShipmentUseCase.class,
            RegisterDocumentUseCase.class,
            FindDocumentUseCase.class,
            ActivateDocumentUseCase.class,
            ArchiveDocumentUseCase.class,
            RegisterVehicleUnitUseCase.class,
            FindVehicleUnitUseCase.class,
            ActivateVehicleUnitUseCase.class,
            SuspendVehicleUnitUseCase.class,
            MarkVehicleUnitOutOfServiceUseCase.class,
            DismissVehicleUnitUseCase.class,
            RegisterVehicleCombinationUseCase.class,
            FindVehicleCombinationUseCase.class);

    assertTrue(
        useCasePorts.stream().allMatch(UseCase.class::isAssignableFrom),
        () -> "All current inbound ports must extend the base UseCase contract: " + useCasePorts);
  }

  @Test
  void currentApplicationServicesImplementTheirInboundPorts() {
    assertTrue(RegisterLocationUseCase.class.isAssignableFrom(RegisterLocationService.class));
    assertTrue(FindLocationUseCase.class.isAssignableFrom(FindLocationService.class));
    assertTrue(RegisterCargoUnitUseCase.class.isAssignableFrom(RegisterCargoUnitService.class));
    assertTrue(FindCargoUnitUseCase.class.isAssignableFrom(FindCargoUnitService.class));
    assertTrue(CreateShipmentUseCase.class.isAssignableFrom(CreateShipmentService.class));
    assertTrue(FindShipmentUseCase.class.isAssignableFrom(FindShipmentService.class));
    assertTrue(AddShipmentItemUseCase.class.isAssignableFrom(AddShipmentItemService.class));
    assertTrue(AddShipmentLegUseCase.class.isAssignableFrom(AddShipmentLegService.class));
    assertTrue(ConfirmShipmentUseCase.class.isAssignableFrom(ConfirmShipmentService.class));
    assertTrue(CancelShipmentUseCase.class.isAssignableFrom(CancelShipmentService.class));
    assertTrue(RegisterDocumentUseCase.class.isAssignableFrom(RegisterDocumentService.class));
    assertTrue(FindDocumentUseCase.class.isAssignableFrom(FindDocumentService.class));
    assertTrue(ActivateDocumentUseCase.class.isAssignableFrom(ActivateDocumentService.class));
    assertTrue(ArchiveDocumentUseCase.class.isAssignableFrom(ArchiveDocumentService.class));
    assertTrue(RegisterVehicleUnitUseCase.class.isAssignableFrom(RegisterVehicleUnitService.class));
    assertTrue(FindVehicleUnitUseCase.class.isAssignableFrom(FindVehicleUnitService.class));
    assertTrue(ActivateVehicleUnitUseCase.class.isAssignableFrom(ActivateVehicleUnitService.class));
    assertTrue(SuspendVehicleUnitUseCase.class.isAssignableFrom(SuspendVehicleUnitService.class));
    assertTrue(
        MarkVehicleUnitOutOfServiceUseCase.class.isAssignableFrom(
            MarkVehicleUnitOutOfServiceService.class));
    assertTrue(DismissVehicleUnitUseCase.class.isAssignableFrom(DismissVehicleUnitService.class));
    assertTrue(
        RegisterVehicleCombinationUseCase.class.isAssignableFrom(
            RegisterVehicleCombinationService.class));
    assertTrue(
        FindVehicleCombinationUseCase.class.isAssignableFrom(FindVehicleCombinationService.class));
  }

  @Test
  void applicationResultsRejectNullDomainObjectsWithApplicationValidationErrors() {
    assertThrows(UseCaseValidationException.class, () -> LocationResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> CargoUnitResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> ShipmentResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> DocumentResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> VehicleUnitResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> VehicleCombinationResult.from(null));
  }
}
