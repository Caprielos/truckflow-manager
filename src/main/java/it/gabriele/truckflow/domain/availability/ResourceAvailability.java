package it.gabriele.truckflow.domain.availability;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TimeWindow;

import java.util.Objects;

/**
 * Rappresenta la disponibilità di una risorsa in un intervallo di date e orari.
 */
public final class ResourceAvailability {

    private static final int MAX_RESOURCE_CODE_LENGTH = 50;

    private final AvailabilityResourceType resourceType;
    private final String resourceCode;
    private final DateRange dateRange;
    private final TimeWindow timeWindow;
    private final AvailabilityStatus status;
    private final Notes notes;

    private ResourceAvailability(
            AvailabilityResourceType resourceType,
            String resourceCode,
            DateRange dateRange,
            TimeWindow timeWindow,
            AvailabilityStatus status,
            Notes notes
    ) {
        if (resourceType == null) {
            throw new IllegalArgumentException("Il tipo risorsa è obbligatorio.");
        }

        if (dateRange == null) {
            throw new IllegalArgumentException("L'intervallo di date è obbligatorio.");
        }

        if (timeWindow == null) {
            throw new IllegalArgumentException("La finestra oraria è obbligatoria.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Lo stato disponibilità è obbligatorio.");
        }

        if (notes == null) {
            throw new IllegalArgumentException("Le note disponibilità sono obbligatorie.");
        }

        this.resourceType = resourceType;
        this.resourceCode = validateResourceCode(resourceCode);
        this.dateRange = dateRange;
        this.timeWindow = timeWindow;
        this.status = status;
        this.notes = notes;
    }

    public static ResourceAvailability of(
            AvailabilityResourceType resourceType,
            String resourceCode,
            DateRange dateRange,
            TimeWindow timeWindow,
            AvailabilityStatus status,
            Notes notes
    ) {
        return new ResourceAvailability(
                resourceType,
                resourceCode,
                dateRange,
                timeWindow,
                status,
                notes
        );
    }

    public static ResourceAvailability available(
            AvailabilityResourceType resourceType,
            String resourceCode,
            DateRange dateRange,
            TimeWindow timeWindow,
            Notes notes
    ) {
        return of(resourceType, resourceCode, dateRange, timeWindow, AvailabilityStatus.AVAILABLE, notes);
    }

    public static ResourceAvailability reserved(
            AvailabilityResourceType resourceType,
            String resourceCode,
            DateRange dateRange,
            TimeWindow timeWindow,
            Notes notes
    ) {
        return of(resourceType, resourceCode, dateRange, timeWindow, AvailabilityStatus.RESERVED, notes);
    }

    public static ResourceAvailability assigned(
            AvailabilityResourceType resourceType,
            String resourceCode,
            DateRange dateRange,
            TimeWindow timeWindow,
            Notes notes
    ) {
        return of(resourceType, resourceCode, dateRange, timeWindow, AvailabilityStatus.ASSIGNED, notes);
    }

    public static ResourceAvailability unavailable(
            AvailabilityResourceType resourceType,
            String resourceCode,
            DateRange dateRange,
            TimeWindow timeWindow,
            Notes notes
    ) {
        return of(resourceType, resourceCode, dateRange, timeWindow, AvailabilityStatus.UNAVAILABLE, notes);
    }

    public static ResourceAvailability maintenance(
            AvailabilityResourceType resourceType,
            String resourceCode,
            DateRange dateRange,
            TimeWindow timeWindow,
            Notes notes
    ) {
        return of(resourceType, resourceCode, dateRange, timeWindow, AvailabilityStatus.MAINTENANCE, notes);
    }

    public static ResourceAvailability onLeave(
            AvailabilityResourceType resourceType,
            String resourceCode,
            DateRange dateRange,
            TimeWindow timeWindow,
            Notes notes
    ) {
        return of(resourceType, resourceCode, dateRange, timeWindow, AvailabilityStatus.ON_LEAVE, notes);
    }

    private static String validateResourceCode(String resourceCode) {
        if (resourceCode == null) {
            throw new IllegalArgumentException("Il codice risorsa è obbligatorio.");
        }

        String normalizedResourceCode = resourceCode.trim().toUpperCase();

        if (normalizedResourceCode.isEmpty()) {
            throw new IllegalArgumentException("Il codice risorsa non può essere vuoto.");
        }

        if (normalizedResourceCode.length() > MAX_RESOURCE_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice risorsa non può superare " + MAX_RESOURCE_CODE_LENGTH + " caratteri.");
        }

        if (!normalizedResourceCode.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice risorsa può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedResourceCode;
    }

    public AvailabilityResourceType getResourceType() {
        return resourceType;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public TimeWindow getTimeWindow() {
        return timeWindow;
    }

    public AvailabilityStatus getStatus() {
        return status;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean isAvailable() {
        return status == AvailabilityStatus.AVAILABLE;
    }

    public boolean isBookable() {
        return status.isBookable();
    }

    public boolean isBlocking() {
        return status.isBlocking();
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public boolean isSameResource(ResourceAvailability other) {
        if (other == null) {
            throw new IllegalArgumentException("La disponibilità da confrontare è obbligatoria.");
        }

        return resourceType == other.resourceType
                && resourceCode.equals(other.resourceCode);
    }

    public boolean isSameResource(AvailabilityResourceType resourceType, String resourceCode) {
        if (resourceType == null) {
            throw new IllegalArgumentException("Il tipo risorsa da verificare è obbligatorio.");
        }

        return this.resourceType == resourceType
                && this.resourceCode.equals(validateResourceCode(resourceCode));
    }

    public boolean overlapsWith(ResourceAvailability other) {
        if (other == null) {
            throw new IllegalArgumentException("La disponibilità da confrontare è obbligatoria.");
        }

        return isSameResource(other)
                && dateRange.overlapsWith(other.dateRange)
                && timeWindow.overlapsWith(other.timeWindow);
    }

    public boolean overlapsWith(DateRange requestedDateRange, TimeWindow requestedTimeWindow) {
        if (requestedDateRange == null) {
            throw new IllegalArgumentException("L'intervallo date richiesto è obbligatorio.");
        }

        if (requestedTimeWindow == null) {
            throw new IllegalArgumentException("La finestra oraria richiesta è obbligatoria.");
        }

        return dateRange.overlapsWith(requestedDateRange)
                && timeWindow.overlapsWith(requestedTimeWindow);
    }

    public String formatSingleLine() {
        return resourceType + " - " + resourceCode + " - " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceAvailability that)) return false;
        return resourceType == that.resourceType
                && resourceCode.equals(that.resourceCode)
                && dateRange.equals(that.dateRange)
                && timeWindow.equals(that.timeWindow)
                && status == that.status
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceType, resourceCode, dateRange, timeWindow, status, notes);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
