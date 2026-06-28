package it.gabriele.truckflow.domain.document;

import it.gabriele.truckflow.domain.shared.Notes;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Documento collegato a spedizioni, fatture, mezzi o autisti.
 */
public final class TransportDocument {

    private static final int MAX_CODE_LENGTH = 50;

    private final String documentNumber;
    private final TransportDocumentType type;
    private final String referenceNumber;
    private final LocalDate issueDate;
    private final LocalDate expirationDate;
    private final DocumentStatus status;
    private final Notes notes;

    private TransportDocument(
            String documentNumber,
            TransportDocumentType type,
            String referenceNumber,
            LocalDate issueDate,
            LocalDate expirationDate,
            DocumentStatus status,
            Notes notes
    ) {
        this.documentNumber = validateCode(documentNumber, "Il numero documento è obbligatorio.");

        if (type == null) {
            throw new IllegalArgumentException("Il tipo documento è obbligatorio.");
        }

        this.referenceNumber = validateCode(referenceNumber, "Il riferimento documento è obbligatorio.");

        if (issueDate == null) {
            throw new IllegalArgumentException("La data documento è obbligatoria.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Lo stato documento è obbligatorio.");
        }

        if (notes == null) {
            throw new IllegalArgumentException("Le note documento sono obbligatorie.");
        }

        if (expirationDate != null && expirationDate.isBefore(issueDate)) {
            throw new IllegalArgumentException("La scadenza documento non può essere precedente alla data documento.");
        }

        if ((status == DocumentStatus.RECEIVED || status == DocumentStatus.VERIFIED)
                && type.isExpirable()
                && expirationDate == null) {
            throw new IllegalArgumentException("Questo tipo documento richiede una data di scadenza.");
        }

        this.type = type;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.status = status;
        this.notes = notes;
    }

    public static TransportDocument draft(
            String documentNumber,
            TransportDocumentType type,
            String referenceNumber,
            LocalDate issueDate,
            Notes notes
    ) {
        return new TransportDocument(
                documentNumber,
                type,
                referenceNumber,
                issueDate,
                null,
                DocumentStatus.DRAFT,
                notes
        );
    }

    public static TransportDocument received(
            String documentNumber,
            TransportDocumentType type,
            String referenceNumber,
            LocalDate issueDate,
            LocalDate expirationDate,
            Notes notes
    ) {
        return new TransportDocument(
                documentNumber,
                type,
                referenceNumber,
                issueDate,
                expirationDate,
                DocumentStatus.RECEIVED,
                notes
        );
    }

    public static TransportDocument verified(
            String documentNumber,
            TransportDocumentType type,
            String referenceNumber,
            LocalDate issueDate,
            LocalDate expirationDate,
            Notes notes
    ) {
        return new TransportDocument(
                documentNumber,
                type,
                referenceNumber,
                issueDate,
                expirationDate,
                DocumentStatus.VERIFIED,
                notes
        );
    }

    private static String validateCode(String code, String nullMessage) {
        if (code == null) {
            throw new IllegalArgumentException(nullMessage);
        }

        String normalizedCode = code.trim().toUpperCase();

        if (normalizedCode.isEmpty()) {
            throw new IllegalArgumentException(nullMessage);
        }

        if (normalizedCode.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }

        if (!normalizedCode.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedCode;
    }

    public TransportDocument request() {
        if (!DocumentRules.canBeRequested(this)) {
            throw new IllegalStateException("Il documento non può essere richiesto.");
        }

        return new TransportDocument(
                documentNumber,
                type,
                referenceNumber,
                issueDate,
                expirationDate,
                DocumentStatus.REQUESTED,
                notes
        );
    }

    public TransportDocument receive(LocalDate expirationDate) {
        if (!DocumentRules.canBeReceived(this)) {
            throw new IllegalStateException("Il documento non può essere ricevuto.");
        }

        return new TransportDocument(
                documentNumber,
                type,
                referenceNumber,
                issueDate,
                expirationDate,
                DocumentStatus.RECEIVED,
                notes
        );
    }

    public TransportDocument verify() {
        if (!DocumentRules.canBeVerified(this)) {
            throw new IllegalStateException("Il documento non può essere verificato.");
        }

        return new TransportDocument(
                documentNumber,
                type,
                referenceNumber,
                issueDate,
                expirationDate,
                DocumentStatus.VERIFIED,
                notes
        );
    }

    public TransportDocument reject() {
        if (!DocumentRules.canBeRejected(this)) {
            throw new IllegalStateException("Il documento non può essere rifiutato.");
        }

        return new TransportDocument(
                documentNumber,
                type,
                referenceNumber,
                issueDate,
                expirationDate,
                DocumentStatus.REJECTED,
                notes
        );
    }

    public TransportDocument expire() {
        if (!DocumentRules.canBeExpired(this)) {
            throw new IllegalStateException("Il documento non può essere marcato come scaduto.");
        }

        return new TransportDocument(
                documentNumber,
                type,
                referenceNumber,
                issueDate,
                expirationDate,
                DocumentStatus.EXPIRED,
                notes
        );
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public TransportDocumentType getType() {
        return type;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean hasExpirationDate() {
        return expirationDate != null;
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public boolean isDraft() {
        return status == DocumentStatus.DRAFT;
    }

    public boolean isRequested() {
        return status == DocumentStatus.REQUESTED;
    }

    public boolean isReceived() {
        return status == DocumentStatus.RECEIVED;
    }

    public boolean isVerified() {
        return status == DocumentStatus.VERIFIED;
    }

    public boolean isRejected() {
        return status == DocumentStatus.REJECTED;
    }

    public boolean isExpired() {
        return status == DocumentStatus.EXPIRED;
    }

    public boolean isTerminal() {
        return status.isTerminal();
    }

    public boolean isUsableForOperation() {
        return status.isUsableForOperation();
    }

    public boolean isAdrDocument() {
        return type.isRequiredForAdr();
    }

    public boolean isProofOfDelivery() {
        return type.isProofOfDelivery();
    }

    public String formatSingleLine() {
        return documentNumber
                + " - " + referenceNumber
                + " - " + type
                + " - " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransportDocument that)) return false;
        return documentNumber.equals(that.documentNumber)
                && type == that.type
                && referenceNumber.equals(that.referenceNumber)
                && issueDate.equals(that.issueDate)
                && Objects.equals(expirationDate, that.expirationDate)
                && status == that.status
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentNumber, type, referenceNumber, issueDate, expirationDate, status, notes);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
