package it.gabriele.truckflow.domain.facility;

/**
 * Indica come l'azienda controlla economicamente una struttura fisica.
 */
public enum FacilityOwnershipType {

    OWNED,
    RENTED,
    LEASED,
    THIRD_PARTY_YARD;

    public boolean isOwnedAsset() {
        return this == OWNED;
    }

    public boolean requiresRecurringOccupancyPayment() {
        return this == RENTED || this == LEASED || this == THIRD_PARTY_YARD;
    }
}
