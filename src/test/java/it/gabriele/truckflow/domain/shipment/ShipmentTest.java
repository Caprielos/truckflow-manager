package it.gabriele.truckflow.domain.shipment;

import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.cargo.CargoItem;
import it.gabriele.truckflow.domain.cargo.CargoLoad;
import it.gabriele.truckflow.domain.customer.Customer;
import it.gabriele.truckflow.domain.customer.CustomerAccount;
import it.gabriele.truckflow.domain.customer.CustomerContact;
import it.gabriele.truckflow.domain.customer.CustomerContactRole;
import it.gabriele.truckflow.domain.customer.CustomerType;
import it.gabriele.truckflow.domain.facility.Facility;
import it.gabriele.truckflow.domain.facility.FacilityType;
import it.gabriele.truckflow.domain.location.Address;
import it.gabriele.truckflow.domain.location.Location;
import it.gabriele.truckflow.domain.order.TransportOrder;
import it.gabriele.truckflow.domain.order.TransportServiceType;
import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import it.gabriele.truckflow.domain.shared.Weight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa Shipment.
 */
class ShipmentTest {

    @Test
    void shouldCreateShipmentFromAcceptedOrder() {
        TransportOrder acceptedOrder = acceptedStandardOrder();

        Shipment shipment = Shipment.fromAcceptedOrder(
                "shp-001",
                acceptedOrder,
                Notes.empty()
        );

        assertEquals("SHP-001", shipment.getShipmentNumber());
        assertEquals(acceptedOrder, shipment.getTransportOrder());
        assertEquals(ShipmentStatus.CREATED, shipment.getStatus());
        assertEquals(Notes.empty(), shipment.getNotes());
        assertTrue(shipment.canBePlanned());
    }

    @Test
    void shouldNotCreateShipmentFromNonAcceptedOrder() {
        TransportOrder draftOrder = draftStandardOrder();

        assertThrows(IllegalArgumentException.class, () -> Shipment.fromAcceptedOrder(
                "SHP-001",
                draftOrder,
                Notes.empty()
        ));
    }

    @Test
    void shouldNormalizeShipmentNumber() {
        Shipment shipment = Shipment.fromAcceptedOrder(
                "  shp_001  ",
                acceptedStandardOrder(),
                Notes.empty()
        );

        assertEquals("SHP_001", shipment.getShipmentNumber());
    }

    @Test
    void shouldNotAllowInvalidShipmentNumber() {
        assertThrows(IllegalArgumentException.class, () -> Shipment.fromAcceptedOrder(
                null,
                acceptedStandardOrder(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Shipment.fromAcceptedOrder(
                "SHP 001",
                acceptedStandardOrder(),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> Shipment.fromAcceptedOrder(
                "SHP-001",
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> Shipment.fromAcceptedOrder(
                "SHP-001",
                acceptedStandardOrder(),
                null
        ));
    }

    @Test
    void shouldPlanCreatedShipment() {
        Shipment planned = createdShipment().plan();

        assertEquals(ShipmentStatus.PLANNED, planned.getStatus());
    }

    @Test
    void shouldNotPlanNonCreatedShipment() {
        Shipment planned = createdShipment().plan();

        assertThrows(IllegalStateException.class, planned::plan);
    }

    @Test
    void shouldDispatchPlannedShipment() {
        Shipment dispatched = createdShipment()
                .plan()
                .dispatch();

        assertEquals(ShipmentStatus.DISPATCHED, dispatched.getStatus());
    }

    @Test
    void shouldNotDispatchUnplannedShipment() {
        Shipment shipment = createdShipment();

        assertThrows(IllegalStateException.class, shipment::dispatch);
    }

    @Test
    void shouldMarkDispatchedShipmentInTransit() {
        Shipment inTransit = createdShipment()
                .plan()
                .dispatch()
                .markInTransit();

        assertEquals(ShipmentStatus.IN_TRANSIT, inTransit.getStatus());
    }

    @Test
    void shouldDeliverInTransitShipment() {
        Shipment delivered = createdShipment()
                .plan()
                .dispatch()
                .markInTransit()
                .deliver();

        assertEquals(ShipmentStatus.DELIVERED, delivered.getStatus());
    }

    @Test
    void shouldNotDeliverShipmentBeforeTransit() {
        Shipment shipment = createdShipment().plan();

        assertThrows(IllegalStateException.class, shipment::deliver);
    }

    @Test
    void shouldCancelNonTerminalShipment() {
        Shipment cancelled = createdShipment().cancel();

        assertEquals(ShipmentStatus.CANCELLED, cancelled.getStatus());
    }

    @Test
    void shouldNotCancelTerminalShipment() {
        Shipment delivered = createdShipment()
                .plan()
                .dispatch()
                .markInTransit()
                .deliver();

        assertThrows(IllegalStateException.class, delivered::cancel);
    }

    @Test
    void shouldExposeTransportOrderData() {
        Shipment shipment = createdShipment();

        assertEquals("CUST-001", shipment.getCustomerAccount().getCustomerCode());
        assertEquals(CargoCategory.GENERAL, shipment.getCargoLoad().getItems().get(0).getCategory());
        assertEquals("MIL-WH-01", shipment.getPickupFacility().getCode());
        assertEquals("ROM-WH-01", shipment.getDeliveryFacility().getCode());
    }

    @Test
    void shouldDetectInternationalShipment() {
        Shipment shipment = Shipment.fromAcceptedOrder(
                "SHP-001",
                acceptedInternationalOrder(),
                Notes.empty()
        );

        assertTrue(shipment.isInternational());
    }

    @Test
    void shouldDetectTemperatureControlRequirement() {
        Shipment shipment = Shipment.fromAcceptedOrder(
                "SHP-001",
                acceptedRefrigeratedOrder(),
                Notes.empty()
        );

        assertTrue(shipment.requiresTemperatureControlledTransport());
    }

    @Test
    void shouldDetectHazardousMaterial() {
        Shipment shipment = Shipment.fromAcceptedOrder(
                "SHP-001",
                acceptedHazardousOrder(),
                Notes.empty()
        );

        assertTrue(shipment.containsHazardousMaterial());
    }

    @Test
    void shouldFormatSingleLine() {
        Shipment shipment = createdShipment();

        assertEquals("SHP-001 - ORD-001 - CREATED", shipment.formatSingleLine());
    }

    @Test
    void shouldConsiderEquivalentShipmentsEqual() {
        Shipment first = createdShipment();
        Shipment second = createdShipment();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    private static Shipment createdShipment() {
        return Shipment.fromAcceptedOrder(
                "SHP-001",
                acceptedStandardOrder(),
                Notes.empty()
        );
    }

    private static TransportOrder draftStandardOrder() {
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
        );
    }

    private static TransportOrder acceptedStandardOrder() {
        return draftStandardOrder()
                .submit()
                .accept();
    }

    private static TransportOrder acceptedInternationalOrder() {
        return TransportOrder.draft(
                "ORD-001",
                activeCustomerAccount(),
                standardCargoLoad(),
                pickupFacility(),
                frenchDeliveryFacility(),
                TimeWindow.of("08:00", "12:00"),
                TimeWindow.of("14:00", "18:00"),
                TransportServiceType.STANDARD,
                Money.of("1600.00", "EUR"),
                Notes.empty()
        ).submit().accept();
    }

    private static TransportOrder acceptedRefrigeratedOrder() {
        return TransportOrder.draft(
                "ORD-001",
                activeCustomerAccount(),
                refrigeratedCargoLoad(),
                pickupFacility(),
                deliveryFacility(),
                TimeWindow.of("08:00", "12:00"),
                TimeWindow.of("14:00", "18:00"),
                TransportServiceType.REFRIGERATED,
                Money.of("1800.00", "EUR"),
                Notes.empty()
        ).submit().accept();
    }

    private static TransportOrder acceptedHazardousOrder() {
        return TransportOrder.draft(
                "ORD-001",
                activeCustomerAccount(),
                hazardousCargoLoad(),
                pickupFacility(),
                deliveryFacility(),
                TimeWindow.of("08:00", "12:00"),
                TimeWindow.of("14:00", "18:00"),
                TransportServiceType.HAZARDOUS,
                Money.of("2200.00", "EUR"),
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
                Weight.ofKilograms(500),
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        ));
    }

    private static CargoLoad refrigeratedCargoLoad() {
        return CargoLoad.of(CargoItem.temperatureControlled(
                "Latte fresco",
                CargoCategory.REFRIGERATED_FOOD,
                Weight.ofKilograms(300),
                Dimension.ofMeters(2, 1, 1),
                TemperatureRange.ofCelsius(2, 8),
                Notes.empty()
        ));
    }

    private static CargoLoad hazardousCargoLoad() {
        return CargoLoad.of(CargoItem.of(
                "Prodotto chimico",
                CargoCategory.HAZARDOUS_MATERIAL,
                Weight.ofKilograms(200),
                Dimension.ofMeters(1, 1, 1),
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

    private static Facility frenchDeliveryFacility() {
        return Facility.active(
                "PAR-WH-01",
                FacilityType.WAREHOUSE,
                Location.of(
                        "Warehouse Paris",
                        Address.of("Rue de Paris 1", "Paris", "75000", "FR"),
                        "Europe/Paris"
                ),
                TimeWindow.of("08:00", "18:00"),
                Notes.empty()
        );
    }
}
