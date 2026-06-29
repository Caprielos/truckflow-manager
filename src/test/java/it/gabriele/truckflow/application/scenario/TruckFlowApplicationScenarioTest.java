package it.gabriele.truckflow.application.scenario;

import it.gabriele.truckflow.application.port.in.AssignParkingSpotUseCase;
import it.gabriele.truckflow.application.port.in.CalculateDriverMissionPayrollUseCase;
import it.gabriele.truckflow.application.port.in.CalculateMissionEconomicsUseCase;
import it.gabriele.truckflow.application.port.in.CreateShipmentFromAcceptedOrderUseCase;
import it.gabriele.truckflow.application.port.in.PlanTransportMissionUseCase;
import it.gabriele.truckflow.application.port.in.RecordInventoryStockMovementUseCase;
import it.gabriele.truckflow.application.usecase.DefaultAssignParkingSpotUseCase;
import it.gabriele.truckflow.application.usecase.DefaultCalculateDriverMissionPayrollUseCase;
import it.gabriele.truckflow.application.usecase.DefaultCalculateMissionEconomicsUseCase;
import it.gabriele.truckflow.application.usecase.DefaultCloseTransportMissionUseCase;
import it.gabriele.truckflow.application.usecase.DefaultCreateShipmentFromAcceptedOrderUseCase;
import it.gabriele.truckflow.application.usecase.DefaultPlanTransportMissionUseCase;
import it.gabriele.truckflow.application.usecase.DefaultRecordInventoryStockMovementUseCase;
import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.cargo.CargoItem;
import it.gabriele.truckflow.domain.cargo.CargoLoad;
import it.gabriele.truckflow.domain.customer.Customer;
import it.gabriele.truckflow.domain.customer.CustomerAccount;
import it.gabriele.truckflow.domain.customer.CustomerContact;
import it.gabriele.truckflow.domain.customer.CustomerContactRole;
import it.gabriele.truckflow.domain.customer.CustomerType;
import it.gabriele.truckflow.domain.driver.Driver;
import it.gabriele.truckflow.domain.driver.DriverAdrCertificateType;
import it.gabriele.truckflow.domain.driver.DriverLicenseCategory;
import it.gabriele.truckflow.domain.driver.DriverOperationalQualification;
import it.gabriele.truckflow.domain.driver.DriverProfessionalQualification;
import it.gabriele.truckflow.domain.economics.EconomicsRules;
import it.gabriele.truckflow.domain.economics.MissionCostLine;
import it.gabriele.truckflow.domain.economics.MissionCostType;
import it.gabriele.truckflow.domain.economics.MissionEconomics;
import it.gabriele.truckflow.domain.economics.MissionRevenueLine;
import it.gabriele.truckflow.domain.facility.Facility;
import it.gabriele.truckflow.domain.facility.FacilityType;
import it.gabriele.truckflow.domain.fleet.TireSpecification;
import it.gabriele.truckflow.domain.fleet.Vehicle;
import it.gabriele.truckflow.domain.fleet.VehicleBodyBaseType;
import it.gabriele.truckflow.domain.fleet.VehicleBodyType;
import it.gabriele.truckflow.domain.fleet.VehicleCombination;
import it.gabriele.truckflow.domain.fleet.VehicleCombinationType;
import it.gabriele.truckflow.domain.fleet.VehicleStatus;
import it.gabriele.truckflow.domain.fleet.VehicleType;
import it.gabriele.truckflow.domain.inventory.InventoryBalance;
import it.gabriele.truckflow.domain.inventory.InventoryItem;
import it.gabriele.truckflow.domain.inventory.InventoryItemType;
import it.gabriele.truckflow.domain.inventory.InventoryRules;
import it.gabriele.truckflow.domain.inventory.InventoryStockMovement;
import it.gabriele.truckflow.domain.inventory.StockMovementType;
import it.gabriele.truckflow.domain.inventory.WarehouseLocation;
import it.gabriele.truckflow.domain.location.Address;
import it.gabriele.truckflow.domain.location.Location;
import it.gabriele.truckflow.domain.operation.TransportMission;
import it.gabriele.truckflow.domain.operation.TransportMissionStatus;
import it.gabriele.truckflow.domain.order.TransportOrder;
import it.gabriele.truckflow.domain.order.TransportServiceType;
import it.gabriele.truckflow.domain.parking.ParkedResource;
import it.gabriele.truckflow.domain.parking.ParkingAssignment;
import it.gabriele.truckflow.domain.parking.ParkingRules;
import it.gabriele.truckflow.domain.parking.ParkingSpot;
import it.gabriele.truckflow.domain.parking.ParkingSpotType;
import it.gabriele.truckflow.domain.payroll.DriverMissionPayroll;
import it.gabriele.truckflow.domain.payroll.DriverMissionWorkReport;
import it.gabriele.truckflow.domain.payroll.DriverPayComponentType;
import it.gabriele.truckflow.domain.payroll.DriverPayRule;
import it.gabriele.truckflow.domain.payroll.DriverPayUnit;
import it.gabriele.truckflow.domain.payroll.DriverPayrollPolicy;
import it.gabriele.truckflow.domain.payroll.DriverPayrollRules;
import it.gabriele.truckflow.domain.route.RoutePlan;
import it.gabriele.truckflow.domain.route.RouteStop;
import it.gabriele.truckflow.domain.route.RouteStopType;
import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.Percentage;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import it.gabriele.truckflow.domain.shared.Weight;
import it.gabriele.truckflow.domain.shipment.Shipment;
import it.gabriele.truckflow.infrastructure.memory.InMemoryDriverMissionPayrollRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryDriverMissionWorkReportRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryDriverPayrollPolicyRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryDriverRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryInventoryBalanceRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryInventoryItemRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryInventoryStockMovementRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryMissionEconomicsRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryParkingAssignmentRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryParkingSpotRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRoutePlanRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryShipmentRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryTransportMissionRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryTransportOrderRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryVehicleCombinationRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryWarehouseLocationRepository;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test di scenario: qui non testiamo una singola classe domain, ma un piccolo flusso reale
 * usando application use case + repository in memoria.
 */
class TruckFlowApplicationScenarioTest {

    @Test
    void shouldAssignReadyCombinationToParkingSpotUsingApplicationUseCase() {
        InMemoryParkingSpotRepository spotRepository = new InMemoryParkingSpotRepository();
        InMemoryParkingAssignmentRepository assignmentRepository = new InMemoryParkingAssignmentRepository();

        ParkingSpot spot = ParkingSpot.available(
                "DEPOT-MIL-01",
                "A12",
                ParkingSpotType.FULL_COMBINATION_SPACE,
                18.75,
                3.20,
                true,
                Notes.of("Posto lungo con presa corrente per semirimorchio frigo")
        );
        spotRepository.save(spot);

        ParkedResource readyCombination = ParkedResource.articulatedVehicle(
                "COMBO-001",
                "TRACTOR-001",
                "SEMI-001",
                "Trattore + semirimorchio agganciati e pronti",
                16.50,
                true
        );

        DefaultAssignParkingSpotUseCase useCase = new DefaultAssignParkingSpotUseCase(spotRepository, assignmentRepository);
        ParkingAssignment assignment = useCase.handle(new AssignParkingSpotUseCase.Command(
                "PARK-001",
                "DEPOT-MIL-01:A12",
                readyCombination,
                LocalDateTime.of(2026, 6, 29, 18, 30),
                Notes.of("Convoglio pronto per partenza domani mattina")
        ));

        assertTrue(assignment.parksCombination());
        assertTrue(assignment.isReadyForMission());
        assertTrue(ParkingRules.isReadyCombinationParked(assignment));
        assertTrue(assignmentRepository.findById("PARK-001").isPresent());
    }

    @Test
    void shouldCreateShipmentPlanMissionAndCloseItUsingMemoryRepositories() {
        InMemoryTransportOrderRepository orderRepository = new InMemoryTransportOrderRepository();
        InMemoryShipmentRepository shipmentRepository = new InMemoryShipmentRepository();
        InMemoryDriverRepository driverRepository = new InMemoryDriverRepository();
        InMemoryVehicleCombinationRepository combinationRepository = new InMemoryVehicleCombinationRepository();
        InMemoryRoutePlanRepository routePlanRepository = new InMemoryRoutePlanRepository();
        InMemoryTransportMissionRepository missionRepository = new InMemoryTransportMissionRepository();

        TransportOrder acceptedOrder = acceptedStandardOrder();
        orderRepository.save(acceptedOrder);
        driverRepository.save(standardDriver());
        combinationRepository.save(curtainSideCombination());
        routePlanRepository.save(standardRoutePlan());

        DefaultCreateShipmentFromAcceptedOrderUseCase createShipment = new DefaultCreateShipmentFromAcceptedOrderUseCase(
                orderRepository,
                shipmentRepository
        );
        Shipment shipment = createShipment.handle(new CreateShipmentFromAcceptedOrderUseCase.Command(
                "SHP-APP-001",
                "ORD-APP-001",
                Notes.of("Spedizione creata da ordine accettato")
        ));

        DefaultPlanTransportMissionUseCase planMission = new DefaultPlanTransportMissionUseCase(
                shipmentRepository,
                driverRepository,
                combinationRepository,
                routePlanRepository,
                missionRepository
        );
        TransportMission planned = planMission.handle(new PlanTransportMissionUseCase.Command(
                "MIS-APP-001",
                shipment.getShipmentNumber(),
                "DRV-001",
                "COMBO-CURTAIN",
                "RTE-001",
                Notes.of("Missione pianificata da use case application")
        ));

        missionRepository.save(planned.dispatch().start());
        TransportMission completed = new DefaultCloseTransportMissionUseCase(missionRepository)
                .handle(new it.gabriele.truckflow.application.port.in.CloseTransportMissionUseCase.Command("MIS-APP-001"));

        assertEquals(TransportMissionStatus.COMPLETED, completed.getStatus());
        assertTrue(shipmentRepository.findById("SHP-APP-001").isPresent());
        assertTrue(missionRepository.findById("MIS-APP-001").isPresent());
    }

    @Test
    void shouldCalculateMissionEconomicsAndDriverPayrollUsingUseCases() {
        InMemoryShipmentRepository shipmentRepository = new InMemoryShipmentRepository();
        InMemoryTransportMissionRepository missionRepository = new InMemoryTransportMissionRepository();
        InMemoryMissionEconomicsRepository economicsRepository = new InMemoryMissionEconomicsRepository();
        InMemoryDriverMissionWorkReportRepository workReportRepository = new InMemoryDriverMissionWorkReportRepository();
        InMemoryDriverPayrollPolicyRepository policyRepository = new InMemoryDriverPayrollPolicyRepository();
        InMemoryDriverMissionPayrollRepository payrollRepository = new InMemoryDriverMissionPayrollRepository();

        Shipment shipment = Shipment.fromAcceptedOrder("SHP-ECON-001", acceptedStandardOrder(), Notes.empty());
        TransportMission mission = TransportMission.planned(
                "MIS-ECON-001",
                shipment,
                standardDriver(),
                curtainSideCombination(),
                standardRoutePlan(),
                Notes.empty()
        );
        shipmentRepository.save(shipment);
        missionRepository.save(mission);

        DriverMissionWorkReport report = DriverMissionWorkReport.builder("WR-001", mission.getMissionNumber(), standardDriver())
                .drivingTime(Duration.ofHours(8))
                .otherWorkTime(Duration.ofHours(2))
                .waitingTime(Duration.ofHours(1))
                .loadingUnloadingTime(Duration.ofHours(1))
                .overtime(Duration.ofHours(1))
                .nightWorkTime(Duration.ofHours(2))
                .internationalAllowanceDays(1)
                .overnightDays(1)
                .cargoCategories(Set.of(CargoCategory.WASTE_DANGEROUS, CargoCategory.FUEL))
                .vehicleContext(VehicleCombinationType.ARTICULATED_VEHICLE, VehicleBodyBaseType.TANK)
                .build();
        DriverPayrollPolicy policy = realisticPayrollPolicy();
        workReportRepository.save(report);
        policyRepository.save(policy);

        DriverMissionPayroll payroll = new DefaultCalculateDriverMissionPayrollUseCase(
                workReportRepository,
                policyRepository,
                payrollRepository
        ).handle(new CalculateDriverMissionPayrollUseCase.Command(
                "PAY-001",
                "WR-001",
                "PAYPOL-2026",
                Notes.of("Costo autista da scenario application")
        ));

        MissionEconomics economics = new DefaultCalculateMissionEconomicsUseCase(
                missionRepository,
                shipmentRepository,
                economicsRepository
        ).handle(new CalculateMissionEconomicsUseCase.Command(
                mission.getMissionNumber(),
                shipment.getShipmentNumber(),
                List.of(MissionRevenueLine.baseTransportFee("REV-BASE", "Trasporto Milano Roma", Money.of("1450", "EUR"), Notes.empty())),
                List.of(
                        MissionCostLine.of("COST-FUEL", MissionCostType.FUEL, "Gasolio", Money.of("310", "EUR"), Notes.empty()),
                        MissionCostLine.of("COST-TOLL", MissionCostType.TOLL, "Pedaggi", Money.of("140", "EUR"), Notes.empty()),
                        MissionCostLine.of("COST-DRIVER", MissionCostType.DRIVER_WAGE, "Costo autista", payroll.calculateTotalEmployerCost(), Notes.empty())
                ),
                Notes.of("Economics missione con costo autista reale")
        ));

        assertTrue(payrollRepository.findById("PAY-001").isPresent());
        assertTrue(economicsRepository.findById("MIS-ECON-001").isPresent());
        assertTrue(EconomicsRules.isMissionProfitable(economics));
        assertTrue(DriverPayrollRules.missionHasDriverCost(
                it.gabriele.truckflow.domain.payroll.MissionPayrollProjection.fromPayroll(payroll, "DRV-COST-001", Notes.empty())
        ));
    }

    @Test
    void shouldRegisterInventoryMovementsAndUpdateWarehouseBalance() {
        InMemoryInventoryItemRepository itemRepository = new InMemoryInventoryItemRepository();
        InMemoryWarehouseLocationRepository locationRepository = new InMemoryWarehouseLocationRepository();
        InMemoryInventoryStockMovementRepository movementRepository = new InMemoryInventoryStockMovementRepository();
        InMemoryInventoryBalanceRepository balanceRepository = new InMemoryInventoryBalanceRepository();

        InventoryItem brakePads = InventoryItem.of(
                "PAD-001",
                InventoryItemType.BRAKE_COMPONENT,
                "Pastiglie freno asse trattore",
                "pcs",
                Money.of("85.00", "EUR"),
                4,
                Notes.empty()
        );
        WarehouseLocation location = WarehouseLocation.of("DEPOT-MI", "A", "S01", "B10");
        itemRepository.save(brakePads);
        locationRepository.save(location);

        DefaultRecordInventoryStockMovementUseCase useCase = new DefaultRecordInventoryStockMovementUseCase(
                itemRepository,
                locationRepository,
                movementRepository,
                balanceRepository
        );
        InventoryBalance afterPurchase = useCase.handle(new RecordInventoryStockMovementUseCase.Command(
                brakePads.getItemCode(),
                location.getFullCode(),
                InventoryStockMovement.of(
                        "MOV-001",
                        brakePads.getItemCode(),
                        location,
                        StockMovementType.PURCHASE_IN,
                        6,
                        Money.of("85.00", "EUR"),
                        LocalDateTime.of(2026, 6, 1, 9, 0),
                        "INV-001",
                        Notes.empty()
                )
        ));
        InventoryBalance afterConsumption = useCase.handle(new RecordInventoryStockMovementUseCase.Command(
                brakePads.getItemCode(),
                location.getFullCode(),
                InventoryStockMovement.of(
                        "MOV-002",
                        brakePads.getItemCode(),
                        location,
                        StockMovementType.CONSUMPTION_MAINTENANCE,
                        3,
                        Money.of("85.00", "EUR"),
                        LocalDateTime.of(2026, 6, 5, 11, 0),
                        "WO-001",
                        Notes.empty()
                )
        ));

        assertEquals(6.0, afterPurchase.getAvailableQuantity());
        assertEquals(3.0, afterConsumption.getAvailableQuantity());
        assertTrue(InventoryRules.shouldReorder(afterConsumption));
        assertFalse(afterConsumption.canReserve(4));
    }

    private static TransportOrder acceptedStandardOrder() {
        return TransportOrder.draft(
                "ORD-APP-001",
                activeCustomerAccount(),
                standardCargoLoad(),
                pickupFacility(),
                deliveryFacility(),
                TimeWindow.of("08:00", "12:00"),
                TimeWindow.of("14:00", "18:00"),
                TransportServiceType.STANDARD,
                Money.of("1200.00", "EUR"),
                Notes.empty()
        ).submit().accept();
    }

    private static Driver standardDriver() {
        return Driver.available(
                "DRV-001",
                "Mario Rossi",
                Set.of(DriverLicenseCategory.CE),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(DriverAdrCertificateType.ADR_BASIC, DriverAdrCertificateType.ADR_TANK),
                Set.of(DriverOperationalQualification.INTERNATIONAL_TRANSPORT, DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT),
                Notes.empty()
        );
    }

    private static VehicleCombination curtainSideCombination() {
        return VehicleCombination.withTrailer(
                "COMBO-CURTAIN",
                tractorUnit(),
                curtainSideTrailer(),
                Notes.empty()
        );
    }

    private static Vehicle tractorUnit() {
        return Vehicle.nonCargoVehicle(
                "TRACTOR-001",
                "TR 001 AA",
                "JH4KA8260MC000000",
                VehicleType.TRACTOR_UNIT,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Notes.empty()
        );
    }

    private static Vehicle curtainSideTrailer() {
        return Vehicle.cargoVehicle(
                "TRAILER-001",
                "TRL 001",
                "VF1BBBBB555123456",
                VehicleType.SEMI_TRAILER,
                VehicleBodyType.CURTAIN_SIDE,
                VehicleStatus.AVAILABLE,
                standardTire(),
                Weight.ofKilograms(25000),
                Dimension.ofMeters(13.6, 2.5, 3.2),
                null,
                Notes.empty()
        );
    }

    private static TireSpecification standardTire() {
        return TireSpecification.of("Michelin", "X Multi", "315/70 R22.5", 154, "L");
    }

    private static RoutePlan standardRoutePlan() {
        return RoutePlan.of(
                "RTE-001",
                List.of(
                        RouteStop.of(1, RouteStopType.START, pickupFacility(), TimeWindow.of("07:00", "08:00"), Notes.empty()),
                        RouteStop.of(2, RouteStopType.PICKUP, pickupFacility(), TimeWindow.of("08:00", "09:00"), Notes.empty()),
                        RouteStop.of(3, RouteStopType.DELIVERY, deliveryFacility(), TimeWindow.of("14:00", "15:00"), Notes.empty()),
                        RouteStop.of(4, RouteStopType.END, deliveryFacility(), TimeWindow.of("16:00", "17:00"), Notes.empty())
                ),
                Distance.ofKilometers(580),
                Notes.empty()
        );
    }

    private static CustomerAccount activeCustomerAccount() {
        return CustomerAccount.of(
                Customer.active("CUST-001", "ACME Logistics S.r.l.", CustomerType.COMPANY, customerLocation(), Notes.empty()),
                CustomerContact.primary("Mario Rossi", CustomerContactRole.LOGISTICS, "mario.rossi@example.com", "+39 333 1234567", Notes.empty())
        );
    }

    private static Location customerLocation() {
        return Location.of("Sede Cliente Milano", Address.of("Via Cliente 10", "Milano", "20100", "IT"), "Europe/Rome");
    }

    private static CargoLoad standardCargoLoad() {
        return CargoLoad.of(CargoItem.of(
                "Merce generale",
                CargoCategory.GENERAL,
                Weight.ofKilograms(5000),
                Dimension.ofMeters(8, 2, 2),
                Notes.empty()
        ));
    }

    private static Facility pickupFacility() {
        return Facility.active(
                "MIL-WH-01",
                FacilityType.WAREHOUSE,
                Location.of("Magazzino Milano", Address.of("Via Roma 10", "Milano", "20100", "IT"), "Europe/Rome"),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );
    }

    private static Facility deliveryFacility() {
        return Facility.active(
                "ROM-WH-01",
                FacilityType.WAREHOUSE,
                Location.of("Magazzino Roma", Address.of("Via Appia 20", "Roma", "00100", "IT"), "Europe/Rome"),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );
    }

    private static DriverPayrollPolicy realisticPayrollPolicy() {
        return DriverPayrollPolicy.of(
                "PAYPOL-2026",
                "Politica paga trasporto pesante 2026",
                LocalDate.of(2026, 1, 1),
                null,
                List.of(
                        amount("BASE_DRIVE", DriverPayComponentType.BASE_DRIVING_TIME, DriverPayUnit.PER_HOUR, "Guida ordinaria", "20.00"),
                        amount("BASE_WORK", DriverPayComponentType.BASE_OTHER_WORK, DriverPayUnit.PER_HOUR, "Lavoro non guida", "18.00"),
                        amount("WAITING", DriverPayComponentType.WAITING_TIME, DriverPayUnit.PER_HOUR, "Attesa", "12.00"),
                        amount("LOAD_UNLOAD", DriverPayComponentType.LOADING_UNLOADING, DriverPayUnit.PER_HOUR, "Carico scarico", "18.00"),
                        amount("OVERTIME", DriverPayComponentType.OVERTIME, DriverPayUnit.PER_HOUR, "Straordinario", "30.00"),
                        amount("NIGHT", DriverPayComponentType.NIGHT_WORK, DriverPayUnit.PER_HOUR, "Maggiorazione notturna", "8.00"),
                        amount("INT_ALLOW", DriverPayComponentType.INTERNATIONAL_DAILY_ALLOWANCE, DriverPayUnit.PER_DAY, "Diaria estera", "60.00"),
                        amount("OVERNIGHT", DriverPayComponentType.OVERNIGHT_ALLOWANCE, DriverPayUnit.PER_DAY, "Pernottamento", "45.00"),
                        amount("CE_PREM", DriverPayComponentType.LICENSE_CE_PREMIUM, DriverPayUnit.PER_MISSION, "Premio CE", "25.00"),
                        amount("CQC_PREM", DriverPayComponentType.CQC_GOODS_PREMIUM, DriverPayUnit.PER_MISSION, "Premio CQC merci", "10.00"),
                        amount("ADR_BASIC", DriverPayComponentType.ADR_BASIC_PREMIUM, DriverPayUnit.PER_HOUR, "Premio ADR base", "5.00"),
                        amount("ADR_TANK", DriverPayComponentType.ADR_TANK_PREMIUM, DriverPayUnit.PER_HOUR, "Premio ADR cisterna", "4.00"),
                        amount("DANGER_WASTE", DriverPayComponentType.DANGEROUS_WASTE_PREMIUM, DriverPayUnit.PER_HOUR, "Premio rifiuti pericolosi", "6.00"),
                        amount("ARTIC", DriverPayComponentType.ARTICULATED_VEHICLE_PREMIUM, DriverPayUnit.PER_MISSION, "Premio articolato", "20.00"),
                        amount("SEMI", DriverPayComponentType.SEMI_TRAILER_PREMIUM, DriverPayUnit.PER_MISSION, "Premio semirimorchio", "15.00"),
                        amount("TANK_BODY", DriverPayComponentType.TANK_BODY_PREMIUM, DriverPayUnit.PER_MISSION, "Premio cisterna", "18.00"),
                        percentage("CONTRIB", DriverPayComponentType.SOCIAL_CONTRIBUTIONS, "Contributi aziendali", "25")
                ),
                Notes.empty()
        );
    }

    private static DriverPayRule amount(String code, DriverPayComponentType type, DriverPayUnit unit, String description, String amount) {
        return DriverPayRule.amount(code, type, unit, description, Money.of(amount, "EUR"), Notes.empty());
    }

    private static DriverPayRule percentage(String code, DriverPayComponentType type, String description, String percentage) {
        return DriverPayRule.percentageOfBase(code, type, description, Percentage.of(percentage), Notes.empty());
    }
}
