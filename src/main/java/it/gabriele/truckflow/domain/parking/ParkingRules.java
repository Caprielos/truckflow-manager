package it.gabriele.truckflow.domain.parking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Regole operative per parcheggi, piazzali e disponibilità dei posti.
 */
public final class ParkingRules {

    private ParkingRules() {
    }

    public static boolean canPark(ParkingSpot spot, ParkedResource resource) {
        if (spot == null) {
            throw new IllegalArgumentException("Il posto parcheggio è obbligatorio.");
        }
        if (resource == null) {
            throw new IllegalArgumentException("La risorsa parcheggiata è obbligatoria.");
        }
        return spot.canReceive(resource);
    }

    public static boolean isReadyCombinationParked(ParkingAssignment assignment) {
        if (assignment == null) {
            throw new IllegalArgumentException("L'assegnazione parcheggio è obbligatoria.");
        }
        return assignment.parksCombination() && assignment.isReadyForMission();
    }

    public static boolean isSpotFreeAt(
            String facilityCode,
            String spotNumber,
            LocalDateTime moment,
            List<ParkingAssignment> assignments
    ) {
        if (assignments == null) {
            throw new IllegalArgumentException("Le assegnazioni parcheggio sono obbligatorie.");
        }
        if (assignments.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Le assegnazioni parcheggio non possono contenere elementi null.");
        }
        if (moment == null) {
            throw new IllegalArgumentException("Il momento verifica posto è obbligatorio.");
        }

        return assignments.stream()
                .noneMatch(assignment -> assignment.occupiesSpot(facilityCode, spotNumber) && assignment.isActiveAt(moment));
    }

    public static boolean isResourceAlreadyParkedAt(
            String resourceId,
            LocalDateTime moment,
            List<ParkingAssignment> assignments
    ) {
        if (assignments == null) {
            throw new IllegalArgumentException("Le assegnazioni parcheggio sono obbligatorie.");
        }
        if (assignments.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Le assegnazioni parcheggio non possono contenere elementi null.");
        }
        if (moment == null) {
            throw new IllegalArgumentException("Il momento verifica risorsa è obbligatorio.");
        }

        return assignments.stream()
                .anyMatch(assignment -> assignment.containsResource(resourceId) && assignment.isActiveAt(moment));
    }
}
