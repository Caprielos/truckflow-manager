package it.gabriele.truckflow.shipment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShipmentStatusRulesTest {

    private final ShipmentStatusRules rules = new ShipmentStatusRules();

    @Test
    void shouldAllowCreatedShipmentToBecomeAssigned() {
        boolean result = rules.canMoveTo(
                ShipmentStatus.CREATED,
                ShipmentStatus.ASSIGNED
        );

        assertTrue(result);
    }

    @Test
    void shouldAllowAssignedShipmentToGoInTransit() {
        boolean result = rules.canMoveTo(
                ShipmentStatus.ASSIGNED,
                ShipmentStatus.IN_TRANSIT
        );

        assertTrue(result);
    }

    @Test
    void shouldAllowInTransitShipmentToBecomeDelivered() {
        boolean result = rules.canMoveTo(
                ShipmentStatus.IN_TRANSIT,
                ShipmentStatus.DELIVERED
        );

        assertTrue(result);
    }

    @Test
    void shouldNotAllowDeliveredShipmentToBecomeCreatedAgain() {
        boolean result = rules.canMoveTo(
                ShipmentStatus.DELIVERED,
                ShipmentStatus.CREATED
        );

        assertFalse(result);
    }

    @Test
    void shouldNotAllowNullStatuses() {
        boolean result = rules.canMoveTo(null, ShipmentStatus.CREATED);

        assertFalse(result);
    }
}
