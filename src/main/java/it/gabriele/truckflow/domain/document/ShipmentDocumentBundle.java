package it.gabriele.truckflow.domain.document;

import it.gabriele.truckflow.domain.shared.Notes;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Fascicolo documentale di una spedizione: documenti richiesti e documenti presenti.
 */
public final class ShipmentDocumentBundle {

    private static final int MAX_CODE_LENGTH = 50;

    private final String bundleCode;
    private final String shipmentNumber;
    private final Set<TransportDocumentType> requiredTypes;
    private final List<TransportDocument> documents;
    private final Notes notes;

    private ShipmentDocumentBundle(
            String bundleCode,
            String shipmentNumber,
            Set<TransportDocumentType> requiredTypes,
            List<TransportDocument> documents,
            Notes notes
    ) {
        this.bundleCode = validateCode(bundleCode, "Il codice fascicolo documentale è obbligatorio.");
        this.shipmentNumber = validateCode(shipmentNumber, "Il numero spedizione fascicolo è obbligatorio.");
        this.requiredTypes = validateRequiredTypes(requiredTypes);
        this.documents = validateDocuments(documents);
        if (notes == null) {
            throw new IllegalArgumentException("Le note fascicolo documentale sono obbligatorie.");
        }
        this.notes = notes;
    }

    public static ShipmentDocumentBundle of(
            String bundleCode,
            String shipmentNumber,
            Set<TransportDocumentType> requiredTypes,
            List<TransportDocument> documents,
            Notes notes
    ) {
        return new ShipmentDocumentBundle(bundleCode, shipmentNumber, requiredTypes, documents, notes);
    }

    private static Set<TransportDocumentType> validateRequiredTypes(Set<TransportDocumentType> requiredTypes) {
        if (requiredTypes == null) {
            throw new IllegalArgumentException("I tipi documento richiesti sono obbligatori.");
        }
        if (requiredTypes.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("I tipi documento richiesti non possono contenere null.");
        }
        return Set.copyOf(requiredTypes);
    }

    private static List<TransportDocument> validateDocuments(List<TransportDocument> documents) {
        if (documents == null) {
            throw new IllegalArgumentException("I documenti fascicolo sono obbligatori.");
        }
        if (documents.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("I documenti fascicolo non possono contenere null.");
        }
        return List.copyOf(documents);
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
            throw new IllegalArgumentException("Il codice fascicolo non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }
        if (!normalized.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice fascicolo può contenere solo lettere, numeri, trattini e underscore.");
        }
        return normalized;
    }

    public String getBundleCode() { return bundleCode; }
    public String getShipmentNumber() { return shipmentNumber; }
    public Set<TransportDocumentType> getRequiredTypes() { return requiredTypes; }
    public List<TransportDocument> getDocuments() { return documents; }
    public Notes getNotes() { return notes; }

    public Set<TransportDocumentType> presentTypes() {
        return documents.stream().map(TransportDocument::getType).collect(Collectors.toSet());
    }

    public Set<TransportDocumentType> missingRequiredTypes() {
        Set<TransportDocumentType> present = presentTypes();
        return requiredTypes.stream()
                .filter(requiredType -> !present.contains(requiredType))
                .collect(Collectors.toSet());
    }

    public boolean isComplete() {
        return missingRequiredTypes().isEmpty();
    }

    public boolean allPresentDocumentsAreVerified() {
        return !documents.isEmpty() && documents.stream().allMatch(TransportDocument::isVerified);
    }

    public boolean isReadyForOperation() {
        return isComplete() && allPresentDocumentsAreVerified();
    }
}
