package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.command.documents.ActivateDocumentCommand;
import it.gabriele.truckflow.application.command.documents.ArchiveDocumentCommand;
import it.gabriele.truckflow.application.command.documents.FindDocumentCommand;
import it.gabriele.truckflow.application.command.documents.RegisterDocumentCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.usecase.documents.ActivateDocumentService;
import it.gabriele.truckflow.application.usecase.documents.ArchiveDocumentService;
import it.gabriele.truckflow.application.usecase.documents.FindDocumentService;
import it.gabriele.truckflow.application.usecase.documents.RegisterDocumentService;
import it.gabriele.truckflow.domain.documents.DocumentCategory;
import it.gabriele.truckflow.domain.documents.DocumentCode;
import it.gabriele.truckflow.domain.documents.DocumentContent;
import it.gabriele.truckflow.domain.documents.DocumentId;
import it.gabriele.truckflow.domain.documents.DocumentMetadata;
import it.gabriele.truckflow.domain.documents.DocumentReference;
import it.gabriele.truckflow.domain.documents.DocumentReferenceType;
import it.gabriele.truckflow.domain.documents.DocumentStatus;
import it.gabriele.truckflow.domain.documents.DocumentType;
import it.gabriele.truckflow.infrastructure.memory.documents.InMemoryDocumentRepository;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ApplicationUseCaseExpansionTest {

  @Test
  void documentUseCasesRegisterFindActivateAndArchiveLogicalDocuments() {
    var context = new DocumentUseCaseContext();

    var registered = context.registerDocument.execute(documentCommand("DOC-CMR-001"));

    assertEquals(DocumentCode.of("doc-cmr-001"), registered.code());
    assertEquals(DocumentStatus.DRAFT, registered.status());
    assertEquals("CMR Milano Roma", registered.title());
    assertTrue(registered.hasLogicalContent());
    assertEquals(1, registered.referenceCount());

    var found = context.findDocument.execute(new FindDocumentCommand(registered.id()));
    assertEquals(registered, found);

    var active = context.activateDocument.execute(new ActivateDocumentCommand(registered.id()));
    assertEquals(DocumentStatus.ACTIVE, active.status());
    assertEquals(
        DocumentStatus.ACTIVE,
        context.documentRepository.findById(registered.id()).orElseThrow().status());

    var archived = context.archiveDocument.execute(new ArchiveDocumentCommand(registered.id()));
    assertEquals(DocumentStatus.ARCHIVED, archived.status());
    assertEquals(
        DocumentStatus.ARCHIVED,
        context.documentRepository.findById(registered.id()).orElseThrow().status());
  }

  @Test
  void documentUseCasesRejectDuplicateCodesAndMissingResources() {
    var context = new DocumentUseCaseContext();
    context.registerDocument.execute(documentCommand("DOC-DUP-001"));

    assertThrows(
        DuplicateResourceException.class,
        () -> context.registerDocument.execute(documentCommand("doc-dup-001")));
    assertThrows(
        ResourceNotFoundException.class,
        () -> context.findDocument.execute(new FindDocumentCommand(DocumentId.random())));
    assertThrows(
        ResourceNotFoundException.class,
        () -> context.activateDocument.execute(new ActivateDocumentCommand(DocumentId.random())));
    assertThrows(
        ResourceNotFoundException.class,
        () -> context.archiveDocument.execute(new ArchiveDocumentCommand(DocumentId.random())));
  }

  @Test
  void documentUseCasesRejectNullCommandsAndRepositoryDependencies() {
    var context = new DocumentUseCaseContext();

    assertThrows(UseCaseValidationException.class, () -> context.registerDocument.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.findDocument.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.activateDocument.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.archiveDocument.execute(null));

    assertThrows(UseCaseValidationException.class, () -> new RegisterDocumentService(null));
    assertThrows(UseCaseValidationException.class, () -> new FindDocumentService(null));
    assertThrows(UseCaseValidationException.class, () -> new ActivateDocumentService(null));
    assertThrows(UseCaseValidationException.class, () -> new ArchiveDocumentService(null));
  }

  @Test
  void documentCommandsRejectMissingRequiredApplicationInputs() {
    assertThrows(
        UseCaseValidationException.class,
        () ->
            new RegisterDocumentCommand(
                null,
                DocumentType.CMR,
                DocumentCategory.SHIPMENT,
                DocumentStatus.DRAFT,
                DocumentMetadata.minimal("CMR"),
                DocumentContent.empty(),
                Set.of(),
                "Missing code"));
    assertThrows(
        UseCaseValidationException.class,
        () ->
            new RegisterDocumentCommand(
                DocumentCode.of("DOC-INVALID-001"),
                null,
                DocumentCategory.SHIPMENT,
                DocumentStatus.DRAFT,
                DocumentMetadata.minimal("CMR"),
                DocumentContent.empty(),
                Set.of(),
                "Missing type"));
    assertThrows(
        UseCaseValidationException.class,
        () ->
            new RegisterDocumentCommand(
                DocumentCode.of("DOC-INVALID-002"),
                DocumentType.CMR,
                DocumentCategory.SHIPMENT,
                DocumentStatus.DRAFT,
                null,
                DocumentContent.empty(),
                Set.of(),
                "Missing metadata"));
    assertThrows(UseCaseValidationException.class, () -> new FindDocumentCommand(null));
    assertThrows(UseCaseValidationException.class, () -> new ActivateDocumentCommand(null));
    assertThrows(UseCaseValidationException.class, () -> new ArchiveDocumentCommand(null));
  }

  @Test
  void documentRegisterCommandNormalizesNullReferencesToEmptyImmutableSet() {
    var command =
        new RegisterDocumentCommand(
            DocumentCode.of("DOC-NULL-REFERENCES-001"),
            DocumentType.GENERIC,
            DocumentCategory.GENERIC,
            DocumentStatus.DRAFT,
            DocumentMetadata.minimal("Generic document"),
            DocumentContent.empty(),
            null,
            "Null references are accepted as empty references");

    assertTrue(command.references().isEmpty());
    assertThrows(UnsupportedOperationException.class, () -> command.references().add(null));
    assertFalse(command.content().hasContent());
  }

  private static RegisterDocumentCommand documentCommand(String code) {
    return new RegisterDocumentCommand(
        DocumentCode.of(code),
        DocumentType.CMR,
        DocumentCategory.SHIPMENT,
        DocumentStatus.DRAFT,
        new DocumentMetadata(
            "CMR Milano Roma",
            "Operations office",
            "Logical CMR document for a shipment",
            "1.0",
            Set.of("cmr", "shipment")),
        new DocumentContent(
            "CMR logical body", "CMR summary", "Content is logical only, not a physical PDF"),
        Set.of(new DocumentReference(DocumentReferenceType.SHIPMENT, "SHP-001", "Linked shipment")),
        "Application 6G document use case test");
  }

  private static final class DocumentUseCaseContext {

    private final InMemoryDocumentRepository documentRepository = new InMemoryDocumentRepository();

    private final RegisterDocumentService registerDocument =
        new RegisterDocumentService(documentRepository);
    private final FindDocumentService findDocument = new FindDocumentService(documentRepository);
    private final ActivateDocumentService activateDocument =
        new ActivateDocumentService(documentRepository);
    private final ArchiveDocumentService archiveDocument =
        new ArchiveDocumentService(documentRepository);
  }
}
