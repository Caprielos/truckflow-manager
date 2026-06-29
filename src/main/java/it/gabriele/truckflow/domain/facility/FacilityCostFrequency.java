package it.gabriele.truckflow.domain.facility;

/**
 * Frequenza con cui un costo di struttura incide economicamente.
 */
public enum FacilityCostFrequency {

    ONE_TIME,
    MONTHLY,
    YEARLY;

    public boolean isRecurring() {
        return this == MONTHLY || this == YEARLY;
    }
}
