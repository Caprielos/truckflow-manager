package it.gabriele.truckflow.domain.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

class DocumentDomainTest {

  @Test
  void createsActiveShipmentDocumentWithGenericReference() {
    var document = shipmentDocument();

    assertTrue(document.isActive());
    assertEquals(DocumentCode.of("CMR-SHP-001"), document.code());
    assertEquals(DocumentType.CMR, document.type());
    assertEquals(DocumentCategory.SHIPMENT, document.category());
    assertTrue(document.hasLogicalContent());
    assertTrue(document.hasReference(DocumentReferenceType.SHIPMENT, "SHP-001"));
  }

  @Test
  void draftDocumentCanExistWithoutLogicalContentAndReferences() {
    var document =
        new Document(
            null,
            DocumentCode.of("DOC-DRAFT"),
            DocumentType.GENERIC,
            DocumentCategory.GENERIC,
            DocumentStatus.DRAFT,
            DocumentMetadata.minimal("Draft document"),
            null,
            Set.of(),
            "Draft note");

    assertTrue(document.isDraft());
    assertFalse(document.hasLogicalContent());
    assertTrue(document.references().isEmpty());
  }

  @Test
  void documentReferenceIsGenericAndDoesNotDependOnOtherDomainIds() {
    var reference =
        new DocumentReference(
            DocumentReferenceType.VEHICLE, "VEH-001", "Generic vehicle reference");

    assertEquals(DocumentReferenceType.VEHICLE, reference.referenceType());
    assertEquals("VEH-001", reference.referencedId());
    assertTrue(reference.references(DocumentReferenceType.VEHICLE, "VEH-001"));
  }

  @Test
  void documentCanChangeStatusWithoutWorkflowConcepts() {
    var document = shipmentDocument();

    document.suspend();
    assertEquals(DocumentStatus.SUSPENDED, document.status());

    document.archive();
    assertTrue(document.isArchived());
  }

  @Test
  void metadataTagsAreNormalized() {
    var metadata =
        new DocumentMetadata(
            "CMR", "Mario Rossi", "Transport document", "v1", Set.of(" ADR ", "Shipment"));

    assertTrue(metadata.hasTag("adr"));
    assertTrue(metadata.hasTag("SHIPMENT"));
  }

  @Test
  void blankDocumentCodeIsRejected() {
    assertThrows(IllegalArgumentException.class, () -> DocumentCode.of("   "));
  }

  @Test
  void blankDocumentReferenceIdIsRejected() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new DocumentReference(DocumentReferenceType.CARGO, " ", "Invalid reference"));
  }

  @Test
  void blankMetadataTitleIsRejected() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new DocumentMetadata(" ", "author", "description", "v1", Set.of()));
  }

  private static Document shipmentDocument() {
    return new Document(
        null,
        DocumentCode.of("cmr-shp-001"),
        DocumentType.CMR,
        DocumentCategory.SHIPMENT,
        DocumentStatus.ACTIVE,
        new DocumentMetadata(
            "CMR shipment document",
            "Gabriele",
            "Logical CMR document registered for a shipment",
            "v1",
            Set.of("shipment", "cmr")),
        new DocumentContent("CMR logical body", "CMR summary", "No physical file here"),
        Set.of(
            new DocumentReference(DocumentReferenceType.SHIPMENT, "SHP-001", "Shipment reference")),
        "Document domain note");
  }
}
