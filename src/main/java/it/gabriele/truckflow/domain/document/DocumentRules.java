package it.gabriele.truckflow.domain.document;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Regole di dominio per i documenti di trasporto.
 */
public final class DocumentRules {

    private DocumentRules() {
    }

    public static boolean canBeRequested(TransportDocument document) {
        validateDocument(document);

        return document.getStatus() == DocumentStatus.DRAFT;
    }

    public static boolean canBeReceived(TransportDocument document) {
        validateDocument(document);

        return document.getStatus() == DocumentStatus.DRAFT
                || document.getStatus() == DocumentStatus.REQUESTED;
    }

    public static boolean canBeVerified(TransportDocument document) {
        validateDocument(document);

        return document.getStatus() == DocumentStatus.RECEIVED;
    }

    public static boolean canBeRejected(TransportDocument document) {
        validateDocument(document);

        return document.getStatus() == DocumentStatus.REQUESTED
                || document.getStatus() == DocumentStatus.RECEIVED;
    }

    public static boolean canBeExpired(TransportDocument document) {
        validateDocument(document);

        return document.getStatus() == DocumentStatus.VERIFIED
                && document.hasExpirationDate();
    }

    public static boolean isExpiredOn(
            TransportDocument document,
            LocalDate referenceDate
    ) {
        validateDocument(document);

        if (referenceDate == null) {
            throw new IllegalArgumentException("La data di riferimento è obbligatoria.");
        }

        return document.hasExpirationDate()
                && referenceDate.isAfter(document.getExpirationDate());
    }

    public static boolean isValidForOperation(
            TransportDocument document,
            LocalDate referenceDate
    ) {
        validateDocument(document);

        return document.isVerified()
                && !isExpiredOn(document, referenceDate);
    }

    public static boolean requiresExpirationDate(TransportDocument document) {
        validateDocument(document);

        return document.getType().isExpirable();
    }

    public static boolean containsAdrDocument(List<TransportDocument> documents) {
        validateDocuments(documents);

        return documents.stream().anyMatch(TransportDocument::isAdrDocument);
    }

    public static boolean containsProofOfDelivery(List<TransportDocument> documents) {
        validateDocuments(documents);

        return documents.stream().anyMatch(TransportDocument::isProofOfDelivery);
    }

    public static boolean allDocumentsValidForOperation(
            List<TransportDocument> documents,
            LocalDate referenceDate
    ) {
        validateDocuments(documents);

        if (documents.isEmpty()) {
            return false;
        }

        return documents.stream()
                .allMatch(document -> isValidForOperation(document, referenceDate));
    }

    private static void validateDocument(TransportDocument document) {
        if (document == null) {
            throw new IllegalArgumentException("Il documento è obbligatorio.");
        }
    }

    private static void validateDocuments(List<TransportDocument> documents) {
        if (documents == null) {
            throw new IllegalArgumentException("La lista documenti è obbligatoria.");
        }

        if (documents.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("La lista documenti non può contenere valori nulli.");
        }
    }
}
