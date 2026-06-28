package it.gabriele.truckflow.domain.availability;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.TimeWindow;

import java.util.List;
import java.util.Objects;

/**
 * Regole per verificare la disponibilità delle risorse.
 */
public final class AvailabilityRules {

    private AvailabilityRules() {
    }

    public static boolean isResourceAvailableForWindow(
            List<ResourceAvailability> availabilityRecords,
            AvailabilityResourceType resourceType,
            String resourceCode,
            DateRange requestedDateRange,
            TimeWindow requestedTimeWindow
    ) {
        validateRecords(availabilityRecords);
        validateRequestedWindow(resourceType, resourceCode, requestedDateRange, requestedTimeWindow);

        boolean hasAvailableSlot = availabilityRecords.stream()
                .filter(record -> record.isSameResource(resourceType, resourceCode))
                .filter(record -> record.overlapsWith(requestedDateRange, requestedTimeWindow))
                .anyMatch(ResourceAvailability::isBookable);

        boolean hasBlockingSlot = availabilityRecords.stream()
                .filter(record -> record.isSameResource(resourceType, resourceCode))
                .filter(record -> record.overlapsWith(requestedDateRange, requestedTimeWindow))
                .anyMatch(ResourceAvailability::isBlocking);

        return hasAvailableSlot && !hasBlockingSlot;
    }

    public static boolean hasBlockingRecordForWindow(
            List<ResourceAvailability> availabilityRecords,
            AvailabilityResourceType resourceType,
            String resourceCode,
            DateRange requestedDateRange,
            TimeWindow requestedTimeWindow
    ) {
        validateRecords(availabilityRecords);
        validateRequestedWindow(resourceType, resourceCode, requestedDateRange, requestedTimeWindow);

        return availabilityRecords.stream()
                .filter(record -> record.isSameResource(resourceType, resourceCode))
                .filter(record -> record.overlapsWith(requestedDateRange, requestedTimeWindow))
                .anyMatch(ResourceAvailability::isBlocking);
    }

    public static boolean canAddAvailabilityRecord(
            List<ResourceAvailability> existingRecords,
            ResourceAvailability newRecord
    ) {
        validateRecords(existingRecords);

        if (newRecord == null) {
            throw new IllegalArgumentException("La nuova disponibilità è obbligatoria.");
        }

        return existingRecords.stream()
                .filter(existingRecord -> existingRecord.isSameResource(newRecord))
                .filter(existingRecord -> existingRecord.overlapsWith(newRecord))
                .noneMatch(existingRecord -> existingRecord.isBlocking() || newRecord.isBlocking());
    }

    public static List<ResourceAvailability> findRecordsForResource(
            List<ResourceAvailability> availabilityRecords,
            AvailabilityResourceType resourceType,
            String resourceCode
    ) {
        validateRecords(availabilityRecords);

        if (resourceType == null) {
            throw new IllegalArgumentException("Il tipo risorsa è obbligatorio.");
        }

        if (resourceCode == null || resourceCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Il codice risorsa è obbligatorio.");
        }

        return availabilityRecords.stream()
                .filter(record -> record.isSameResource(resourceType, resourceCode))
                .toList();
    }

    private static void validateRecords(List<ResourceAvailability> availabilityRecords) {
        if (availabilityRecords == null) {
            throw new IllegalArgumentException("La lista delle disponibilità è obbligatoria.");
        }

        if (availabilityRecords.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("La lista delle disponibilità non può contenere valori nulli.");
        }
    }

    private static void validateRequestedWindow(
            AvailabilityResourceType resourceType,
            String resourceCode,
            DateRange requestedDateRange,
            TimeWindow requestedTimeWindow
    ) {
        if (resourceType == null) {
            throw new IllegalArgumentException("Il tipo risorsa è obbligatorio.");
        }

        if (resourceCode == null || resourceCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Il codice risorsa è obbligatorio.");
        }

        if (requestedDateRange == null) {
            throw new IllegalArgumentException("L'intervallo date richiesto è obbligatorio.");
        }

        if (requestedTimeWindow == null) {
            throw new IllegalArgumentException("La finestra oraria richiesta è obbligatoria.");
        }
    }
}
