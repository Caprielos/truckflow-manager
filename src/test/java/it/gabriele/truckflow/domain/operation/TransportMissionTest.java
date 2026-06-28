package it.gabriele.truckflow.domain.operation;

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
import it.gabriele.truckflow.domain.facility.Facility;
import it.gabriele.truckflow.domain.facility.FacilityType;
import it.gabriele.truckflow.domain.fleet.TireSpecification;
import it.gabriele.truckflow.domain.fleet.Vehicle;
import it.gabriele.truckflow.domain.fleet.VehicleBodyType;
import it.gabriele.truckflow.domain.fleet.VehicleCombination;
import it.gabriele.truckflow.domain.fleet.VehicleStatus;
import it.gabriele.truckflow.domain.fleet.VehicleType;
import it.gabriele.truckflow.domain.location.Address;
import it.gabriele.truckflow.domain.location.Location;
import it.gabriele.truckflow.domain.order.TransportOrder;
import it.gabriele.truckflow.domain.order.TransportServiceType;
import it.gabriele.truckflow.domain.route.RoutePlan;
import it.gabriele.truckflow.domain.route.RouteStop;
import it.gabriele.truckflow.domain.route.RouteStopType;
import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import it.gabriele.truckflow.domain.shared.Weight;
import it.gabriele.truckflow.domain.shipment.Shipment;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa TransportMission.
 */
class TransportMissionTest {

    @Test
    void shouldCreatePlannedTransportMission() {
        TransportMission mission = standardMission();

        assertEquals("MIS-001", mission.getMissionNumber());
        assertEquals(standardShipment(), mission.getShipment());
        assertEquals(standardDriver(), mission.getDriver());
        assertEquals(curtainSideCombination(), mission.getVehicleCombination());
        assertEquals(standardRoutePlan(), mission.getRoutePlan());
        assertEquals(TransportMissionStatus.PLANNED, mission.getStatus());
        assertTrue(mission.isPlanned());
        assertFalse(mission.isTerminal());
    }

    @Test
    void shouldNormalizeMissionNumber() {
        TransportMission mission = TransportMission.planned(
                "  mis_001  ",
                standardShipment(),
                standardDriver(),
                curtainSideCombination(),
                standardRoutePlan(),
                Notes.empty()
        );

        assertEquals("MIS_001", mission.getMissionNumber());
    }

    @Test
    void shouldRejectInvalidMissionNumber() {
        assertThrows(IllegalArgumentException.class, () -> TransportMission.planned(
                null,
                standardShipment(),
                standardDriver(),
                curtainSideCombination(),
                standardRoutePlan(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TransportMission.planned(
                "MIS 001",
                standardShipment(),
                standardDriver(),
                curtainSideCombination(),
                standardRoutePlan(),
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> TransportMission.planned(
                "MIS-001",
                null,
                standardDriver(),
                curtainSideCombination(),
                standardRoutePlan(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TransportMission.planned(
                "MIS-001",
                standardShipment(),
                null,
                curtainSideCombination(),
                standardRoutePlan(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TransportMission.planned(
                "MIS-001",
                standardShipment(),
                standardDriver(),
                null,
                standardRoutePlan(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TransportMission.planned(
                "MIS-001",
                standardShipment(),
                standardDriver(),
                curtainSideCombination(),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TransportMission.planned(
                "MIS-001",
                standardShipment(),
                standardDriver(),
                curtainSideCombination(),
                standardRoutePlan(),
                null
        ));
    }

    @Test
    void shouldRejectNonCompliantAssignment() {
        assertThrows(IllegalArgumentException.class, () -> TransportMission.planned(
                "MIS-001",
                standardShipment(),
                driverWithoutCqc(),
                curtainSideCombination(),
                standardRoutePlan(),
                Notes.empty()
        ));
    }

    @Test
    void shouldMoveThroughOperationalLifecycle() {
        TransportMission planned = standardMission();

        TransportMission dispatched = planned.dispatch();
        TransportMission inProgress = dispatched.start();
        TransportMission completed = inProgress.complete();

        assertTrue(dispatched.isDispatched());
        assertTrue(inProgress.isInProgress());
        assertTrue(completed.isCompleted());
        assertTrue(completed.isTerminal());
    }

    @Test
    void shouldNotAllowInvalidLifecycleTransitions() {
        TransportMission planned = standardMission();

        assertThrows(IllegalStateException.class, planned::start);
        assertThrows(IllegalStateException.class, planned::complete);

        TransportMission dispatched = planned.dispatch();

        assertThrows(IllegalStateException.class, dispatched::dispatch);
        assertThrows(IllegalStateException.class, dispatched::complete);

        TransportMission completed = dispatched.start().complete();

        assertThrows(IllegalStateException.class, completed::dispatch);
        assertThrows(IllegalStateException.class, completed::start);
        assertThrows(IllegalStateException.class, completed::complete);
    }

    @Test
    void shouldCancelNonTerminalMission() {
        TransportMission planned = standardMission();
        TransportMission dispatched = planned.dispatch();

        assertTrue(planned.cancel().isCancelled());
        assertTrue(dispatched.cancel().isCancelled());
    }

    @Test
    void shouldNotCancelTerminalMission() {
        TransportMission completed = standardMission()
                .dispatch()
                .start()
                .complete();

        TransportMission cancelled = standardMission().cancel();

        assertThrows(IllegalStateException.class, completed::cancel);
        assertThrows(IllegalStateException.class, cancelled::cancel);
    }

    @Test
    void shouldExposeShipmentDelegatedInformation() {
        TransportMission mission = standardMission();

        assertFalse(mission.isInternational());
        assertFalse(mission.requiresTemperatureControlledTransport());
        assertFalse(mission.containsHazardousMaterial());
        assertFalse(mission.requiresSpecialComplianceChecks());
    }

    @Test
    void shouldDetectNotes() {
        TransportMission mission = TransportMission.planned(
                "MIS-001",
                standardShipment(),
                standardDriver(),
                curtainSideCombination(),
                standardRoutePlan(),
                Notes.of("Missione prioritaria")
        );

        assertTrue(mission.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        TransportMission mission = standardMission();

        assertEquals(
                "MIS-001 - shipment: SHP-001 - driver: DRV-001 - vehicle: COMBO-CURTAIN - PLANNED",
                mission.formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentMissionsEqual() {
        TransportMission first = standardMission();
        TransportMission second = standardMission();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    private static TransportMission standardMission() {
        return TransportMission.planned(
                "MIS-001",
                standardShipment(),
                standardDriver(),
                curtainSideCombination(),
                standardRoutePlan(),
                Notes.empty()
        );
    }

    private static Driver standardDriver() {
        return Driver.available(
                "DRV-001",
                "Mario Rossi",
                Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(DriverAdrCertificateType.ADR_BASIC, DriverAdrCertificateType.ADR_TANK),
                Set.of(
                        DriverOperationalQualification.INTERNATIONAL_TRANSPORT,
                        DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT
                ),
                Notes.empty()
        );
    }

    private static Driver driverWithoutCqc() {
        return Driver.available(
                "DRV-002",
                "Luigi Bianchi",
                Set.of(DriverLicenseCategory.C, DriverLicenseCategory.E),
                Set.of(),
                Set.of(DriverAdrCertificateType.ADR_BASIC),
                Set.of(),
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
        return TireSpecification.of(
                "Michelin",
                "X Multi",
                "315/70 R22.5",
                154,
                "L"
        );
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

    private static Shipment standardShipment() {
        return Shipment.fromAcceptedOrder(
                "SHP-001",
                acceptedStandardOrder(),
                Notes.empty()
        );
    }

    private static TransportOrder acceptedStandardOrder() {
        return TransportOrder.draft(
                "ORD-001",
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

    private static CustomerAccount activeCustomerAccount() {
        return CustomerAccount.of(
                Customer.active(
                        "CUST-001",
                        "ACME Logistics S.r.l.",
                        CustomerType.COMPANY,
                        customerLocation(),
                        Notes.empty()
                ),
                primaryContact()
        );
    }

    private static CustomerContact primaryContact() {
        return CustomerContact.primary(
                "Mario Rossi",
                CustomerContactRole.LOGISTICS,
                "mario.rossi@example.com",
                "+39 333 1234567",
                Notes.empty()
        );
    }

    private static Location customerLocation() {
        return Location.of(
                "Sede Cliente Milano",
                Address.of("Via Cliente 10", "Milano", "20100", "IT"),
                "Europe/Rome"
        );
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
                Location.of(
                        "Magazzino Milano",
                        Address.of("Via Roma 10", "Milano", "20100", "IT"),
                        "Europe/Rome"
                ),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );
    }

    private static Facility deliveryFacility() {
        return Facility.active(
                "ROM-WH-01",
                FacilityType.WAREHOUSE,
                Location.of(
                        "Magazzino Roma",
                        Address.of("Via Appia 20", "Roma", "00100", "IT"),
                        "Europe/Rome"
                ),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );
    }
}
