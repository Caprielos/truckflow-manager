package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.port.in.cargo.FindCargoUnitUseCase;
import it.gabriele.truckflow.application.port.in.cargo.RegisterCargoUnitUseCase;
import it.gabriele.truckflow.application.port.in.compliance.ActivateComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.in.compliance.ArchiveComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.in.compliance.DiscontinueComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.in.compliance.FindComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.in.compliance.RegisterComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.in.compliance.SuspendComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.in.documents.ActivateDocumentUseCase;
import it.gabriele.truckflow.application.port.in.documents.ArchiveDocumentUseCase;
import it.gabriele.truckflow.application.port.in.documents.FindDocumentUseCase;
import it.gabriele.truckflow.application.port.in.documents.RegisterDocumentUseCase;
import it.gabriele.truckflow.application.port.in.locations.FindLocationUseCase;
import it.gabriele.truckflow.application.port.in.locations.RegisterLocationUseCase;
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
import it.gabriele.truckflow.application.result.compliance.ComplianceRequirementResult;
import it.gabriele.truckflow.application.result.documents.DocumentResult;
import it.gabriele.truckflow.application.result.locations.LocationResult;
import it.gabriele.truckflow.application.result.operational.DispatcherResult;
import it.gabriele.truckflow.application.result.operational.DriverResult;
import it.gabriele.truckflow.application.result.operational.ManagerResult;
import it.gabriele.truckflow.application.result.operational.MechanicResult;
import it.gabriele.truckflow.application.result.operational.WarehouseOperatorResult;
import it.gabriele.truckflow.application.result.shipments.ShipmentResult;
import it.gabriele.truckflow.application.result.vehicles.VehicleCombinationResult;
import it.gabriele.truckflow.application.result.vehicles.VehicleUnitResult;
import it.gabriele.truckflow.application.usecase.cargo.FindCargoUnitService;
import it.gabriele.truckflow.application.usecase.cargo.RegisterCargoUnitService;
import it.gabriele.truckflow.application.usecase.compliance.ActivateComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.ArchiveComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.DiscontinueComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.FindComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.RegisterComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.SuspendComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.documents.ActivateDocumentService;
import it.gabriele.truckflow.application.usecase.documents.ArchiveDocumentService;
import it.gabriele.truckflow.application.usecase.documents.FindDocumentService;
import it.gabriele.truckflow.application.usecase.documents.RegisterDocumentService;
import it.gabriele.truckflow.application.usecase.locations.FindLocationService;
import it.gabriele.truckflow.application.usecase.locations.RegisterLocationService;
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
            RegisterComplianceRequirementUseCase.class,
            FindComplianceRequirementUseCase.class,
            ActivateComplianceRequirementUseCase.class,
            SuspendComplianceRequirementUseCase.class,
            ArchiveComplianceRequirementUseCase.class,
            DiscontinueComplianceRequirementUseCase.class,
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
            FindVehicleCombinationUseCase.class,
            RegisterDriverUseCase.class,
            FindDriverUseCase.class,
            ActivateDriverUseCase.class,
            SuspendDriverUseCase.class,
            MarkNotEligibleDriverUseCase.class,
            RegisterMechanicUseCase.class,
            FindMechanicUseCase.class,
            ActivateMechanicUseCase.class,
            SuspendMechanicUseCase.class,
            MarkNotEligibleMechanicUseCase.class,
            RegisterWarehouseOperatorUseCase.class,
            FindWarehouseOperatorUseCase.class,
            ActivateWarehouseOperatorUseCase.class,
            SuspendWarehouseOperatorUseCase.class,
            MarkNotEligibleWarehouseOperatorUseCase.class,
            RegisterDispatcherUseCase.class,
            FindDispatcherUseCase.class,
            ActivateDispatcherUseCase.class,
            SuspendDispatcherUseCase.class,
            MarkNotEligibleDispatcherUseCase.class,
            RegisterManagerUseCase.class,
            FindManagerUseCase.class,
            ActivateManagerUseCase.class,
            SuspendManagerUseCase.class,
            MarkNotEligibleManagerUseCase.class);

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
    assertTrue(
        RegisterComplianceRequirementUseCase.class.isAssignableFrom(
            RegisterComplianceRequirementService.class));
    assertTrue(
        FindComplianceRequirementUseCase.class.isAssignableFrom(
            FindComplianceRequirementService.class));
    assertTrue(
        ActivateComplianceRequirementUseCase.class.isAssignableFrom(
            ActivateComplianceRequirementService.class));
    assertTrue(
        SuspendComplianceRequirementUseCase.class.isAssignableFrom(
            SuspendComplianceRequirementService.class));
    assertTrue(
        ArchiveComplianceRequirementUseCase.class.isAssignableFrom(
            ArchiveComplianceRequirementService.class));
    assertTrue(
        DiscontinueComplianceRequirementUseCase.class.isAssignableFrom(
            DiscontinueComplianceRequirementService.class));
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
    assertTrue(RegisterDriverUseCase.class.isAssignableFrom(RegisterDriverService.class));
    assertTrue(FindDriverUseCase.class.isAssignableFrom(FindDriverService.class));
    assertTrue(ActivateDriverUseCase.class.isAssignableFrom(ActivateDriverService.class));
    assertTrue(SuspendDriverUseCase.class.isAssignableFrom(SuspendDriverService.class));
    assertTrue(
        MarkNotEligibleDriverUseCase.class.isAssignableFrom(MarkNotEligibleDriverService.class));
    assertTrue(RegisterMechanicUseCase.class.isAssignableFrom(RegisterMechanicService.class));
    assertTrue(FindMechanicUseCase.class.isAssignableFrom(FindMechanicService.class));
    assertTrue(ActivateMechanicUseCase.class.isAssignableFrom(ActivateMechanicService.class));
    assertTrue(SuspendMechanicUseCase.class.isAssignableFrom(SuspendMechanicService.class));
    assertTrue(
        MarkNotEligibleMechanicUseCase.class.isAssignableFrom(
            MarkNotEligibleMechanicService.class));
    assertTrue(
        RegisterWarehouseOperatorUseCase.class.isAssignableFrom(
            RegisterWarehouseOperatorService.class));
    assertTrue(
        FindWarehouseOperatorUseCase.class.isAssignableFrom(FindWarehouseOperatorService.class));
    assertTrue(
        ActivateWarehouseOperatorUseCase.class.isAssignableFrom(
            ActivateWarehouseOperatorService.class));
    assertTrue(
        SuspendWarehouseOperatorUseCase.class.isAssignableFrom(
            SuspendWarehouseOperatorService.class));
    assertTrue(
        MarkNotEligibleWarehouseOperatorUseCase.class.isAssignableFrom(
            MarkNotEligibleWarehouseOperatorService.class));
    assertTrue(RegisterDispatcherUseCase.class.isAssignableFrom(RegisterDispatcherService.class));
    assertTrue(FindDispatcherUseCase.class.isAssignableFrom(FindDispatcherService.class));
    assertTrue(ActivateDispatcherUseCase.class.isAssignableFrom(ActivateDispatcherService.class));
    assertTrue(SuspendDispatcherUseCase.class.isAssignableFrom(SuspendDispatcherService.class));
    assertTrue(
        MarkNotEligibleDispatcherUseCase.class.isAssignableFrom(
            MarkNotEligibleDispatcherService.class));
    assertTrue(RegisterManagerUseCase.class.isAssignableFrom(RegisterManagerService.class));
    assertTrue(FindManagerUseCase.class.isAssignableFrom(FindManagerService.class));
    assertTrue(ActivateManagerUseCase.class.isAssignableFrom(ActivateManagerService.class));
    assertTrue(SuspendManagerUseCase.class.isAssignableFrom(SuspendManagerService.class));
    assertTrue(
        MarkNotEligibleManagerUseCase.class.isAssignableFrom(MarkNotEligibleManagerService.class));
  }

  @Test
  void applicationResultsRejectNullDomainObjectsWithApplicationValidationErrors() {
    assertThrows(UseCaseValidationException.class, () -> LocationResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> CargoUnitResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> ComplianceRequirementResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> ShipmentResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> DocumentResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> VehicleUnitResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> VehicleCombinationResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> DriverResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> MechanicResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> WarehouseOperatorResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> DispatcherResult.from(null));
    assertThrows(UseCaseValidationException.class, () -> ManagerResult.from(null));
  }
}
