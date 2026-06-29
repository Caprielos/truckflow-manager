package it.gabriele.truckflow.domain.dataimport;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Riga importata da file/API esterna: carta carburante, Telepass, banca, paghe, fatture.
 */
public final class ImportRecord {

    private static final int MAX_CODE_LENGTH = 80;

    private final String recordCode;
    private final ExternalDataSourceType sourceType;
    private final String externalRowId;
    private final ImportRecordStatus status;
    private final String referenceNumber;
    private final Money amount;
    private final LocalDateTime occurredAt;
    private final Notes notes;

    private ImportRecord(
            String recordCode,
            ExternalDataSourceType sourceType,
            String externalRowId,
            ImportRecordStatus status,
            String referenceNumber,
            Money amount,
            LocalDateTime occurredAt,
            Notes notes
    ) {
        this.recordCode = validateCode(recordCode, "Il codice record import è obbligatorio.");
        if (sourceType == null) {
            throw new IllegalArgumentException("Il tipo fonte import è obbligatorio.");
        }
        this.externalRowId = validateCode(externalRowId, "L'id riga esterna import è obbligatorio.");
        if (status == null) {
            throw new IllegalArgumentException("Lo stato record import è obbligatorio.");
        }
        this.referenceNumber = normalizeOptionalCode(referenceNumber);
        if (occurredAt == null) {
            throw new IllegalArgumentException("La data/ora record import è obbligatoria.");
        }
        if (notes == null) {
            throw new IllegalArgumentException("Le note record import sono obbligatorie.");
        }
        this.sourceType = sourceType;
        this.status = status;
        this.amount = amount;
        this.occurredAt = occurredAt;
        this.notes = notes;
    }

    public static ImportRecord of(
            String recordCode,
            ExternalDataSourceType sourceType,
            String externalRowId,
            ImportRecordStatus status,
            String referenceNumber,
            Money amount,
            LocalDateTime occurredAt,
            Notes notes
    ) {
        return new ImportRecord(recordCode, sourceType, externalRowId, status, referenceNumber, amount, occurredAt, notes);
    }

    private static String validateCode(String code, String message) {
        if (code == null) {
            throw new IllegalArgumentException(message);
        }
        String normalized = code.trim().toUpperCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        if (normalized.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice import non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }
        if (!normalized.matches("[A-Z0-9_./-]+")) {
            throw new IllegalArgumentException("Il codice import contiene caratteri non ammessi.");
        }
        return normalized;
    }

    private static String normalizeOptionalCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return "";
        }
        return validateCode(code, "Il riferimento import non è valido.");
    }

    public String getRecordCode() { return recordCode; }
    public ExternalDataSourceType getSourceType() { return sourceType; }
    public String getExternalRowId() { return externalRowId; }
    public ImportRecordStatus getStatus() { return status; }
    public String getReferenceNumber() { return referenceNumber; }
    public Optional<Money> getAmount() { return Optional.ofNullable(amount); }
    public LocalDateTime getOccurredAt() { return occurredAt; }
    public Notes getNotes() { return notes; }

    public boolean hasAmount() {
        return amount != null;
    }

    public boolean canBePostedToDomain() {
        return status.canBePosted();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImportRecord that)) return false;
        return recordCode.equals(that.recordCode)
                && sourceType == that.sourceType
                && externalRowId.equals(that.externalRowId)
                && status == that.status
                && referenceNumber.equals(that.referenceNumber)
                && Objects.equals(amount, that.amount)
                && occurredAt.equals(that.occurredAt)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordCode, sourceType, externalRowId, status, referenceNumber, amount, occurredAt, notes);
    }
}
