package it.gabriele.truckflow.domain.shared;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Rappresenta un intervallo di date del dominio.
 * Serve per validità, contratti, disponibilità e periodi operativi.
 */
public final class DateRange {

    private final LocalDate startDate;
    private final LocalDate endDate;

    private DateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("La data di inizio è obbligatoria.");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("La data di fine è obbligatoria.");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La data di inizio non può essere successiva alla data di fine.");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Crea un intervallo usando LocalDate.
     */
    public static DateRange of(LocalDate startDate, LocalDate endDate) {
        return new DateRange(startDate, endDate);
    }

    /**
     * Crea un intervallo usando stringhe in formato yyyy-MM-dd.
     */
    public static DateRange of(String startDate, String endDate) {
        return new DateRange(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Verifica se una data è compresa nell'intervallo.
     */
    public boolean contains(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("La data da verificare è obbligatoria.");
        }

        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    /**
     * Verifica se questo intervallo si sovrappone a un altro.
     */
    public boolean overlapsWith(DateRange other) {
        if (other == null) {
            throw new IllegalArgumentException("L'intervallo da confrontare è obbligatorio.");
        }

        return !this.endDate.isBefore(other.startDate)
                && !other.endDate.isBefore(this.startDate);
    }

    /**
     * Verifica se questo intervallo è contenuto dentro un altro.
     */
    public boolean isInside(DateRange container) {
        if (container == null) {
            throw new IllegalArgumentException("L'intervallo contenitore è obbligatorio.");
        }

        return !this.startDate.isBefore(container.startDate)
                && !this.endDate.isAfter(container.endDate);
    }

    /**
     * Calcola il numero di giorni inclusi nell'intervallo.
     */
    public long daysInclusive() {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateRange dateRange)) return false;
        return startDate.equals(dateRange.startDate)
                && endDate.equals(dateRange.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @Override
    public String toString() {
        return startDate + " / " + endDate;
    }
}
