package it.gabriele.truckflow.domain.shared;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Rappresenta una finestra oraria del dominio.
 * Serve per ritiri, consegne, appuntamenti e orari operativi.
 */
public final class TimeWindow {

    private final LocalTime startTime;
    private final LocalTime endTime;

    private TimeWindow(LocalTime startTime, LocalTime endTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("L'orario di inizio è obbligatorio.");
        }

        if (endTime == null) {
            throw new IllegalArgumentException("L'orario di fine è obbligatorio.");
        }

        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("L'orario di inizio deve essere precedente all'orario di fine.");
        }

        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Crea una finestra oraria usando LocalTime.
     */
    public static TimeWindow of(LocalTime startTime, LocalTime endTime) {
        return new TimeWindow(startTime, endTime);
    }

    /**
     * Crea una finestra oraria usando stringhe in formato HH:mm.
     */
    public static TimeWindow of(String startTime, String endTime) {
        return new TimeWindow(LocalTime.parse(startTime), LocalTime.parse(endTime));
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Verifica se un orario è dentro la finestra oraria.
     */
    public boolean contains(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("L'orario da verificare è obbligatorio.");
        }

        return !time.isBefore(startTime) && !time.isAfter(endTime);
    }

    /**
     * Verifica se questa finestra oraria si sovrappone a un'altra.
     */
    public boolean overlapsWith(TimeWindow other) {
        if (other == null) {
            throw new IllegalArgumentException("La finestra oraria da confrontare è obbligatoria.");
        }

        return this.startTime.isBefore(other.endTime)
                && other.startTime.isBefore(this.endTime);
    }

    /**
     * Verifica se questa finestra oraria è contenuta dentro un'altra.
     */
    public boolean isInside(TimeWindow container) {
        if (container == null) {
            throw new IllegalArgumentException("La finestra oraria contenitore è obbligatoria.");
        }

        return !this.startTime.isBefore(container.startTime)
                && !this.endTime.isAfter(container.endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeWindow that)) return false;
        return startTime.equals(that.startTime)
                && endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

    @Override
    public String toString() {
        return startTime + " - " + endTime;
    }
}
