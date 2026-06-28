package it.gabriele.truckflow.domain.pricing;

import it.gabriele.truckflow.domain.cargo.AdrClass;
import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.cargo.CargoItem;
import it.gabriele.truckflow.domain.cargo.CargoLoad;
import it.gabriele.truckflow.domain.cargo.DangerousGoodsProfile;
import it.gabriele.truckflow.domain.cargo.HazardLabel;
import it.gabriele.truckflow.domain.cargo.PackingGroup;
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
import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import it.gabriele.truckflow.domain.shared.Weight;
import it.gabriele.truckflow.domain.shipment.Shipment;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa PricingRules.
 */
class PricingRulesTest {

    @Test
    void shouldRequireAdrSurchargeForAdrCargo() {
        assertTrue(PricingRules.requiresAdrSurcharge(adrCargoLoad()));
        assertFalse(PricingRules.requiresAdrSurcharge(standardCargoLoad()));
    }

    @Test
    void shouldRequireTemperatureSurchargeForRefrigeratedCargo() {
        assertTrue(PricingRules.requiresTemperatureControlSurcharge(refrigeratedCargoLoad()));
        assertFalse(PricingRules.requiresTemperatureControlSurcharge(standardCargoLoad()));
    }

    @Test
    void shouldRequireInternationalSurchargeForInternationalShipment() {
        assertTrue(PricingRules.requiresInternationalSurcharge(internationalShipment()));
        assertFalse(PricingRules.requiresInternationalSurcharge(standardShipment()));
    }

    @Test
    void shouldCheckBreakdownRules() {
        PriceBreakdown breakdown = professionalBreakdown();

        assertTrue(PricingRules.hasBaseFreightLine(breakdown));
        assertTrue(PricingRules.hasFuelSurchargeLine(breakdown));
        assertTrue(PricingRules.hasTollChargeLine(breakdown));
        assertTrue(PricingRules.hasVehicleWearChargeLine(breakdown));
        assertTrue(PricingRules.hasAdrSurchargeLine(breakdown));
        assertTrue(PricingRules.hasSurcharges(breakdown));
        assertTrue(PricingRules.hasDiscounts(breakdown));
        assertFalse(PricingRules.hasTemperatureControlSurchargeLine(breakdown));
    }

    @Test
    void shouldNotAllowNullValues() {
        PriceBreakdown breakdown = PriceBreakdown.of(
                "QUOTE-001",
                PricingLine.baseFreight(
                        "LINE-001",
                        "Trasporto base",
                        Money.of("1000.00", "EUR"),
                        Notes.empty()
                )
        );

        assertThrows(IllegalArgumentException.class, () -> PricingRules.requiresAdrSurcharge(null));
        assertThrows(IllegalArgumentException.class, () -> PricingRules.requiresTemperatureControlSurcharge(null));
        assertThrows(IllegalArgumentException.class, () -> PricingRules.requiresInternationalSurcharge(null));
        assertThrows(IllegalArgumentException.class, () -> PricingRules.hasDiscounts(null));
        assertThrows(IllegalArgumentException.class, () -> PricingRules.hasSurcharges(null));
        assertThrows(IllegalArgumentException.class, () -> PricingRules.hasBaseFreightLine(null));
        assertThrows(IllegalArgumentException.class, () -> PricingRules.hasFuelSurchargeLine(null));
        assertThrows(IllegalArgumentException.class, () -> PricingRules.hasTollChargeLine(null));
        assertThrows(IllegalArgumentException.class, () -> PricingRules.hasVehicleWearChargeLine(null));
        assertThrows(IllegalArgumentException.class, () -> PricingRules.hasAdrSurchargeLine(null));
        assertThrows(IllegalArgumentException.class, () -> PricingRules.hasTemperatureControlSurchargeLine(null));

        assertFalse(PricingRules.hasAdrSurchargeLine(breakdown));
    }

    private static PriceBreakdown professionalBreakdown() {
        RouteCostEstimate estimate = RouteCostEstimate.of(
                "EST-001",
                CostEstimationSource.VIAMICHELIN,
                Distance.ofKilometers(580),
                Money.of("220.00", "EUR"),
                Money.of("80.00", "EUR"),
                Money.of("60.00", "EUR"),
                Notes.empty()
        );

        return PriceBreakdown.of(
                "QUOTE-001",
                List.of(
                        PricingLine.baseFreight(
                                "LINE-001",
                                "Trasporto base",
                                Money.of("1000.00", "EUR"),
                                Notes.empty()
                        ),
                        PricingLine.fuelFromEstimate("LINE-002", estimate, Notes.empty()),
                        PricingLine.tollsFromEstimate("LINE-003", estimate, Notes.empty()),
                        PricingLine.vehicleWearFromEstimate("LINE-004", estimate, Notes.empty()),
                        PricingLine.surcharge(
                                "LINE-005",
                                PricingLineType.ADR_SURCHARGE,
                                "Supplemento ADR",
                                Money.of("150.00", "EUR"),
                                Notes.empty()
                        ),
                        PricingLine.discount(
                                "LINE-006",
                                "Sconto cliente",
                                Money.of("160.00", "EUR"),
                                Notes.empty()
                        )
                ),
                Notes.empty()
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

    private static CargoLoad refrigeratedCargoLoad() {
        return CargoLoad.of(CargoItem.temperatureControlled(
                "Latte fresco",
                CargoCategory.REFRIGERATED_FOOD,
                Weight.ofKilograms(5000),
                Dimension.ofMeters(8, 2, 2),
                TemperatureRange.ofCelsius(2, 6),
                Notes.empty()
        ));
    }

    private static CargoLoad adrCargoLoad() {
        return CargoLoad.of(CargoItem.dangerousGoods(
                "Benzina in cisterna",
                CargoCategory.HAZARDOUS_MATERIAL,
                Weight.ofKilograms(10000),
                Dimension.ofMeters(10, 2, 2),
                gasolineProfile(),
                Notes.empty()
        ));
    }

    private static DangerousGoodsProfile gasolineProfile() {
        return DangerousGoodsProfile.of(
                "UN 1203",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                2,
                true
        );
    }

    private static Shipment standardShipment() {
        return Shipment.fromAcceptedOrder(
                "SHP-001",
                acceptedStandardOrder(),
                Notes.empty()
        );
    }

    private static Shipment internationalShipment() {
        return Shipment.fromAcceptedOrder(
                "SHP-002",
                acceptedInternationalOrder(),
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

    private static TransportOrder acceptedInternationalOrder() {
        return TransportOrder.draft(
                "ORD-002",
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
