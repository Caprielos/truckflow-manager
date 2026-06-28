package it.gabriele.truckflow.domain.route;

import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Notes;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Rappresenta un piano di tratta composto da fermate ordinate.
 * Esempio: partenza, ritiro, consegna, arrivo.
 */
public final class RoutePlan {

    private static final int MAX_ROUTE_NUMBER_LENGTH = 50;

    private final String routeNumber;
    private final List<RouteStop> stops;
    private final Distance estimatedDistance;
    private final Notes notes;

    private RoutePlan(
            String routeNumber,
            List<RouteStop> stops,
            Distance estimatedDistance,
            Notes notes
    ) {
        this.routeNumber = validateRouteNumber(routeNumber);
        this.stops = validateAndNormalizeStops(stops);

        if (estimatedDistance == null) {
            throw new IllegalArgumentException("La distanza stimata è obbligatoria.");
        }

        if (notes == null) {
            throw new IllegalArgumentException("Le note della tratta sono obbligatorie.");
        }

        this.estimatedDistance = estimatedDistance;
        this.notes = notes;
    }

    public static RoutePlan of(
            String routeNumber,
            List<RouteStop> stops,
            Distance estimatedDistance,
            Notes notes
    ) {
        return new RoutePlan(routeNumber, stops, estimatedDistance, notes);
    }

    private static String validateRouteNumber(String routeNumber) {
        if (routeNumber == null) {
            throw new IllegalArgumentException("Il numero tratta è obbligatorio.");
        }

        String normalizedRouteNumber = routeNumber.trim().toUpperCase();

        if (normalizedRouteNumber.isEmpty()) {
            throw new IllegalArgumentException("Il numero tratta non può essere vuoto.");
        }

        if (normalizedRouteNumber.length() > MAX_ROUTE_NUMBER_LENGTH) {
            throw new IllegalArgumentException("Il numero tratta non può superare " + MAX_ROUTE_NUMBER_LENGTH + " caratteri.");
        }

        if (!normalizedRouteNumber.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il numero tratta può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedRouteNumber;
    }

    private static List<RouteStop> validateAndNormalizeStops(List<RouteStop> stops) {
        if (stops == null) {
            throw new IllegalArgumentException("La lista delle fermate è obbligatoria.");
        }

        if (stops.size() < 2) {
            throw new IllegalArgumentException("La tratta deve avere almeno una partenza e un arrivo.");
        }

        if (stops.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("La lista delle fermate non può contenere elementi nulli.");
        }

        List<RouteStop> orderedStops = stops.stream()
                .sorted(Comparator.comparingInt(RouteStop::getSequenceNumber))
                .toList();

        for (int i = 0; i < orderedStops.size(); i++) {
            int expectedSequenceNumber = i + 1;

            if (orderedStops.get(i).getSequenceNumber() != expectedSequenceNumber) {
                throw new IllegalArgumentException("Le fermate devono avere numeri di sequenza progressivi a partire da 1.");
            }
        }

        RouteStop firstStop = orderedStops.get(0);
        RouteStop lastStop = orderedStops.get(orderedStops.size() - 1);

        if (!firstStop.isStart()) {
            throw new IllegalArgumentException("La prima fermata deve essere di tipo START.");
        }

        if (!lastStop.isEnd()) {
            throw new IllegalArgumentException("L'ultima fermata deve essere di tipo END.");
        }

        long startCount = orderedStops.stream().filter(RouteStop::isStart).count();
        long endCount = orderedStops.stream().filter(RouteStop::isEnd).count();

        if (startCount != 1) {
            throw new IllegalArgumentException("La tratta deve avere esattamente una fermata START.");
        }

        if (endCount != 1) {
            throw new IllegalArgumentException("La tratta deve avere esattamente una fermata END.");
        }

        return List.copyOf(orderedStops);
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public List<RouteStop> getStops() {
        return stops;
    }

    public Distance getEstimatedDistance() {
        return estimatedDistance;
    }

    public Notes getNotes() {
        return notes;
    }

    public int getStopCount() {
        return stops.size();
    }

    public RouteStop getStartStop() {
        return stops.get(0);
    }

    public RouteStop getEndStop() {
        return stops.get(stops.size() - 1);
    }

    public List<RouteStop> getCargoOperationStops() {
        return stops.stream()
                .filter(RouteStop::isCargoOperation)
                .toList();
    }

    public boolean hasPickupStop() {
        return stops.stream().anyMatch(RouteStop::isPickup);
    }

    public boolean hasDeliveryStop() {
        return stops.stream().anyMatch(RouteStop::isDelivery);
    }

    public boolean isInternational() {
        String startCountry = getStartStop().getFacility().getLocation().getAddress().getCountryCode();
        String endCountry = getEndStop().getFacility().getLocation().getAddress().getCountryCode();

        return !startCountry.equals(endCountry);
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public String formatSingleLine() {
        return routeNumber + " - stops: " + stops.size() + " - " + estimatedDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoutePlan routePlan)) return false;
        return routeNumber.equals(routePlan.routeNumber)
                && stops.equals(routePlan.stops)
                && estimatedDistance.equals(routePlan.estimatedDistance)
                && notes.equals(routePlan.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeNumber, stops, estimatedDistance, notes);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
