package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.shared.Notes;

import java.time.LocalDate;
import java.util.Objects;

public final class VehicleCertificate {

    private final VehicleCertificateType type;
    private final LocalDate validFrom;
    private final LocalDate expiresAt;
    private final Notes notes;

    private VehicleCertificate(VehicleCertificateType type, LocalDate validFrom, LocalDate expiresAt, Notes notes) {
        if (type == null) {
            throw new IllegalArgumentException("Il tipo certificato è obbligatorio.");
        }
        if (expiresAt == null) {
            throw new IllegalArgumentException("La scadenza certificato è obbligatoria.");
        }
        if (validFrom != null && validFrom.isAfter(expiresAt)) {
            throw new IllegalArgumentException("La data inizio validità non può essere successiva alla scadenza.");
        }
        if (notes == null) {
            throw new IllegalArgumentException("Le note certificato sono obbligatorie.");
        }
        this.type = type;
        this.validFrom = validFrom;
        this.expiresAt = expiresAt;
        this.notes = notes;
    }

    public static VehicleCertificate of(VehicleCertificateType type, LocalDate validFrom, LocalDate expiresAt, Notes notes) {
        return new VehicleCertificate(type, validFrom, expiresAt, notes);
    }

    public VehicleCertificateType getType() {
        return type;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getExpiresAt() {
        return expiresAt;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean isValidOn(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("La data di verifica è obbligatoria.");
        }
        return (validFrom == null || !date.isBefore(validFrom)) && !date.isAfter(expiresAt);
    }

    public DeadlineStatus calculateStatus(LocalDate today, int warningDays) {
        if (today == null) {
            throw new IllegalArgumentException("La data odierna è obbligatoria.");
        }
        if (warningDays < 0) {
            throw new IllegalArgumentException("I giorni di preavviso non possono essere negativi.");
        }
        if (today.isAfter(expiresAt)) {
            return DeadlineStatus.EXPIRED;
        }
        if (!today.plusDays(warningDays).isBefore(expiresAt)) {
            return DeadlineStatus.EXPIRING_SOON;
        }
        return DeadlineStatus.VALID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleCertificate that)) return false;
        return type == that.type
                && Objects.equals(validFrom, that.validFrom)
                && expiresAt.equals(that.expiresAt)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, validFrom, expiresAt, notes);
    }
}
