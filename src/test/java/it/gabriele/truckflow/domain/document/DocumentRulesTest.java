package it.gabriele.truckflow.domain.document;

import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa DocumentRules.
 */
class DocumentRulesTest {

    @Test
    void shouldCheckLifecycleRules() {
        TransportDocument draft = draftDocument();
        TransportDocument requested = draft.request();
        TransportDocument received = requested.receive(null);
        TransportDocument verified = received.verify();
        TransportDocument rejected = requested.reject();

        assertTrue(DocumentRules.canBeRequested(draft));
        assertFalse(DocumentRules.canBeRequested(requested));

        assertTrue(DocumentRules.canBeReceived(draft));
        assertTrue(DocumentRules.canBeReceived(requested));
        assertFalse(DocumentRules.canBeReceived(received));

        assertTrue(DocumentRules.canBeVerified(received));
        assertFalse(DocumentRules.canBeVerified(draft));

        assertTrue(DocumentRules.canBeRejected(requested));
        assertTrue(DocumentRules.canBeRejected(received));
        assertFalse(DocumentRules.canBeRejected(verified));

        assertFalse(DocumentRules.canBeExpired(verified));
        assertTrue(rejected.isTerminal());
    }

    @Test
    void shouldCheckExpirationRules() {
        TransportDocument document = TransportDocument.verified(
                "DOC-002",
                TransportDocumentType.INSURANCE_CERTIFICATE,
                "TRUCK-001",
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 12, 31),
                Notes.empty()
        );

        assertTrue(DocumentRules.requiresExpirationDate(document));
        assertFalse(DocumentRules.isExpiredOn(document, LocalDate.of(2026, 12, 31)));
        assertTrue(DocumentRules.isExpiredOn(document, LocalDate.of(2027, 1, 1)));
        assertTrue(DocumentRules.isValidForOperation(document, LocalDate.of(2026, 12, 31)));
        assertFalse(DocumentRules.isValidForOperation(document, LocalDate.of(2027, 1, 1)));
        assertTrue(DocumentRules.canBeExpired(document));
    }

    @Test
    void shouldCheckValidDocumentsList() {
        List<TransportDocument> documents = List.of(
                verifiedCmr(),
                verifiedPod()
        );

        assertTrue(DocumentRules.allDocumentsValidForOperation(
                documents,
                LocalDate.of(2026, 7, 10)
        ));

        assertTrue(DocumentRules.containsProofOfDelivery(documents));
        assertFalse(DocumentRules.containsAdrDocument(documents));
    }

    @Test
    void shouldDetectAdrDocument() {
        List<TransportDocument> documents = List.of(
                verifiedCmr(),
                TransportDocument.verified(
                        "DOC-ADR",
                        TransportDocumentType.ADR_TRANSPORT_DOCUMENT,
                        "SHP-001",
                        LocalDate.of(2026, 7, 1),
                        null,
                        Notes.empty()
                )
        );

        assertTrue(DocumentRules.containsAdrDocument(documents));
    }

    @Test
    void shouldReturnFalseWhenDocumentListIsEmpty() {
        assertFalse(DocumentRules.allDocumentsValidForOperation(
                List.of(),
                LocalDate.of(2026, 7, 10)
        ));
    }

    @Test
    void shouldNotAllowNullValues() {
        TransportDocument document = verifiedCmr();

        assertThrows(IllegalArgumentException.class, () -> DocumentRules.canBeRequested(null));
        assertThrows(IllegalArgumentException.class, () -> DocumentRules.canBeReceived(null));
        assertThrows(IllegalArgumentException.class, () -> DocumentRules.canBeVerified(null));
        assertThrows(IllegalArgumentException.class, () -> DocumentRules.canBeRejected(null));
        assertThrows(IllegalArgumentException.class, () -> DocumentRules.canBeExpired(null));
        assertThrows(IllegalArgumentException.class, () -> DocumentRules.isExpiredOn(null, LocalDate.of(2026, 7, 10)));
        assertThrows(IllegalArgumentException.class, () -> DocumentRules.isExpiredOn(document, null));
        assertThrows(IllegalArgumentException.class, () -> DocumentRules.isValidForOperation(null, LocalDate.of(2026, 7, 10)));
        assertThrows(IllegalArgumentException.class, () -> DocumentRules.requiresExpirationDate(null));
        assertThrows(IllegalArgumentException.class, () -> DocumentRules.containsAdrDocument(null));
        assertThrows(IllegalArgumentException.class, () -> DocumentRules.containsProofOfDelivery(null));
        assertThrows(IllegalArgumentException.class, () -> DocumentRules.allDocumentsValidForOperation(null, LocalDate.of(2026, 7, 10)));
    }

    @Test
    void shouldNotAllowNullDocumentsInsideList() {
        List<TransportDocument> documentsWithNull = Arrays.asList(verifiedCmr(), null);

        assertThrows(IllegalArgumentException.class, () -> DocumentRules.containsAdrDocument(documentsWithNull));
        assertThrows(IllegalArgumentException.class, () -> DocumentRules.allDocumentsValidForOperation(
                documentsWithNull,
                LocalDate.of(2026, 7, 10)
        ));
    }

    @Test
    void shouldExposeEnumDetails() {
        assertTrue(TransportDocumentType.CMR_WAYBILL.isShipmentRelated());
        assertTrue(TransportDocumentType.INVOICE_COPY.isInvoiceRelated());
        assertTrue(TransportDocumentType.ADR_TRANSPORT_DOCUMENT.isRequiredForAdr());
        assertTrue(TransportDocumentType.PROOF_OF_DELIVERY.isProofOfDelivery());
        assertTrue(TransportDocumentType.INSURANCE_CERTIFICATE.isExpirable());

        assertFalse(DocumentStatus.DRAFT.isTerminal());
        assertTrue(DocumentStatus.VERIFIED.isUsableForOperation());
        assertTrue(DocumentStatus.REJECTED.isTerminal());
        assertTrue(DocumentStatus.EXPIRED.isTerminal());
    }

    private static TransportDocument draftDocument() {
        return TransportDocument.draft(
                "DOC-001",
                TransportDocumentType.CMR_WAYBILL,
                "SHP-001",
                LocalDate.of(2026, 7, 1),
                Notes.empty()
        );
    }

    private static TransportDocument verifiedCmr() {
        return TransportDocument.verified(
                "DOC-CMR",
                TransportDocumentType.CMR_WAYBILL,
                "SHP-001",
                LocalDate.of(2026, 7, 1),
                null,
                Notes.empty()
        );
    }

    private static TransportDocument verifiedPod() {
        return TransportDocument.verified(
                "DOC-POD",
                TransportDocumentType.PROOF_OF_DELIVERY,
                "SHP-001",
                LocalDate.of(2026, 7, 2),
                null,
                Notes.empty()
        );
    }
}
