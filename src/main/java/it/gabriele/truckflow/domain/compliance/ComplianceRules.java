package it.gabriele.truckflow.domain.compliance;

import it.gabriele.truckflow.domain.driver.Driver;
import it.gabriele.truckflow.domain.driver.DriverRules;
import it.gabriele.truckflow.domain.fleet.VehicleBodyCompatibilityRules;
import it.gabriele.truckflow.domain.fleet.VehicleCombination;
import it.gabriele.truckflow.domain.fleet.VehicleCombinationRules;
import it.gabriele.truckflow.domain.route.RoutePlan;
import it.gabriele.truckflow.domain.route.RoutePlanRules;
import it.gabriele.truckflow.domain.route.RouteStop;
import it.gabriele.truckflow.domain.shipment.Shipment;

/**
 * Contiene regole di compliance complessive.
 * Collega autista, mezzo, carico, allestimento, tratta e spedizione.
 */
public final class ComplianceRules {

    private ComplianceRules() {
    }

    public static boolean isVehicleCompliantForShipment(
            VehicleCombination vehicleCombination,
            Shipment shipment
    ) {
        validateVehicleCombination(vehicleCombination);
        validateShipment(shipment);

        return VehicleCombinationRules.canBeAssignedToShipment(vehicleCombination, shipment)
                && VehicleBodyCompatibilityRules.isBodyCompatibleWithCargoLoad(
                        vehicleCombination,
                        shipment.getCargoLoad()
                );
    }

    public static boolean isDriverCompliantForShipment(
            Driver driver,
            VehicleCombination vehicleCombination,
            Shipment shipment
    ) {
        validateDriver(driver);
        validateVehicleCombination(vehicleCombination);
        validateShipment(shipment);

        return DriverRules.canBeAssignedToShipment(driver, vehicleCombination, shipment);
    }

    public static boolean isRouteCompliantForShipment(
            RoutePlan routePlan,
            Shipment shipment
    ) {
        validateRoutePlan(routePlan);
        validateShipment(shipment);

        return RoutePlanRules.isOperationallyUsable(routePlan)
                && hasPickupStopForShipment(routePlan, shipment)
                && hasDeliveryStopForShipment(routePlan, shipment);
    }

    public static boolean isAssignmentCompliant(
            Driver driver,
            VehicleCombination vehicleCombination,
            RoutePlan routePlan,
            Shipment shipment
    ) {
        validateDriver(driver);
        validateVehicleCombination(vehicleCombination);
        validateRoutePlan(routePlan);
        validateShipment(shipment);

        return isVehicleCompliantForShipment(vehicleCombination, shipment)
                && isDriverCompliantForShipment(driver, vehicleCombination, shipment)
                && isRouteCompliantForShipment(routePlan, shipment);
    }

    public static boolean requiresSpecialComplianceChecks(Shipment shipment) {
        validateShipment(shipment);

        return shipment.containsHazardousMaterial()
                || shipment.requiresTemperatureControlledTransport()
                || shipment.isInternational();
    }

    private static boolean hasPickupStopForShipment(RoutePlan routePlan, Shipment shipment) {
        return routePlan.getStops().stream()
                .filter(RouteStop::isPickup)
                .anyMatch(stop -> stop.getFacility().equals(shipment.getPickupFacility()));
    }

    private static boolean hasDeliveryStopForShipment(RoutePlan routePlan, Shipment shipment) {
        return routePlan.getStops().stream()
                .filter(RouteStop::isDelivery)
                .anyMatch(stop -> stop.getFacility().equals(shipment.getDeliveryFacility()));
    }

    private static void validateDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("L'autista è obbligatorio.");
        }
    }

    private static void validateVehicleCombination(VehicleCombination vehicleCombination) {
        if (vehicleCombination == null) {
            throw new IllegalArgumentException("La combinazione veicolare è obbligatoria.");
        }
    }

    private static void validateRoutePlan(RoutePlan routePlan) {
        if (routePlan == null) {
            throw new IllegalArgumentException("Il piano di tratta è obbligatorio.");
        }
    }

    private static void validateShipment(Shipment shipment) {
        if (shipment == null) {
            throw new IllegalArgumentException("La spedizione è obbligatoria.");
        }
    }
}
