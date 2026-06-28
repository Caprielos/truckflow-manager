package it.gabriele.truckflow.domain.route;

import it.gabriele.truckflow.domain.shared.Distance;

/**
 * Contiene regole di dominio relative a un piano di tratta.
 */
public final class RoutePlanRules {

    private RoutePlanRules() {
    }

    public static boolean hasCargoOperations(RoutePlan routePlan) {
        if (routePlan == null) {
            throw new IllegalArgumentException("Il piano di tratta è obbligatorio.");
        }

        return !routePlan.getCargoOperationStops().isEmpty();
    }

    public static boolean hasPickupAndDelivery(RoutePlan routePlan) {
        if (routePlan == null) {
            throw new IllegalArgumentException("Il piano di tratta è obbligatorio.");
        }

        return routePlan.hasPickupStop() && routePlan.hasDeliveryStop();
    }

    public static boolean isWithinMaxDistance(RoutePlan routePlan, Distance maxDistance) {
        if (routePlan == null) {
            throw new IllegalArgumentException("Il piano di tratta è obbligatorio.");
        }

        if (maxDistance == null) {
            throw new IllegalArgumentException("La distanza massima è obbligatoria.");
        }

        return routePlan.getEstimatedDistance().isLessThanOrEqualTo(maxDistance);
    }

    public static boolean startsAndEndsAtDifferentFacilities(RoutePlan routePlan) {
        if (routePlan == null) {
            throw new IllegalArgumentException("Il piano di tratta è obbligatorio.");
        }

        return !routePlan.getStartStop().getFacility().equals(routePlan.getEndStop().getFacility());
    }

    public static boolean usesOnlyActiveFacilities(RoutePlan routePlan) {
        if (routePlan == null) {
            throw new IllegalArgumentException("Il piano di tratta è obbligatorio.");
        }

        return routePlan.getStops().stream()
                .allMatch(stop -> stop.getFacility().isActive());
    }

    public static boolean isInternational(RoutePlan routePlan) {
        if (routePlan == null) {
            throw new IllegalArgumentException("Il piano di tratta è obbligatorio.");
        }

        return routePlan.isInternational();
    }

    public static boolean isOperationallyUsable(RoutePlan routePlan) {
        if (routePlan == null) {
            throw new IllegalArgumentException("Il piano di tratta è obbligatorio.");
        }

        return hasPickupAndDelivery(routePlan)
                && startsAndEndsAtDifferentFacilities(routePlan)
                && usesOnlyActiveFacilities(routePlan);
    }
}
