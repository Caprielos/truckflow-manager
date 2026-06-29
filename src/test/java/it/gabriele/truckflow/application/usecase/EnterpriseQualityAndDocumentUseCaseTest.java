package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.usecase.document.DefaultRegisterDocumentVersionUseCase;
import it.gabriele.truckflow.application.usecase.quality.DefaultAssignCorrectiveActionUseCase;
import it.gabriele.truckflow.application.usecase.quality.DefaultCloseQualityEventUseCase;
import it.gabriele.truckflow.application.usecase.quality.DefaultOpenQualityEventUseCase;
import it.gabriele.truckflow.domain.document.DocumentVersion;
import it.gabriele.truckflow.domain.quality.CorrectiveAction;
import it.gabriele.truckflow.domain.quality.QualityEvent;
import it.gabriele.truckflow.domain.quality.QualityEventType;
import it.gabriele.truckflow.domain.quality.QualitySeverity;
import it.gabriele.truckflow.domain.quality.QualityStatus;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.infrastructure.memory.document.InMemoryDocumentVersionRepository;
import it.gabriele.truckflow.infrastructure.memory.quality.InMemoryQualityEventRepository;
import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EnterpriseQualityAndDocumentUseCaseTest {

  @Test
  void shouldManageQualityEventWithCorrectiveAction() {
    InMemoryQualityEventRepository repository = new InMemoryQualityEventRepository();
    QualityEvent event =
        QualityEvent.open(
            "QE-001",
            QualityEventType.CUSTOMER_COMPLAINT,
            QualitySeverity.HIGH,
            "MISSION-001",
            "Reclamo cliente per ritardo",
            Instant.parse("2026-06-29T09:00:00Z"),
            Notes.of("Cliente enterprise"));
    CorrectiveAction action =
        CorrectiveAction.open(
            "CA-001",
            "OPS-001",
            "Analisi causa ritardo",
            LocalDate.of(2026, 7, 3),
            Notes.of("Azione obbligatoria"));

    new DefaultOpenQualityEventUseCase(repository)
        .handle(
            new it.gabriele.truckflow.application.port.in.OpenQualityEventUseCase.Command(event));
    QualityEvent updated =
        new DefaultAssignCorrectiveActionUseCase(repository)
            .handle(
                new it.gabriele.truckflow.application.port.in.AssignCorrectiveActionUseCase.Command(
                    "QE-001", action.complete()));
    QualityEvent closed =
        new DefaultCloseQualityEventUseCase(repository)
            .handle(
                new it.gabriele.truckflow.application.port.in.CloseQualityEventUseCase.Command(
                    "QE-001", Instant.parse("2026-06-30T10:00:00Z")));

    assertEquals(QualityStatus.CORRECTIVE_ACTION_ASSIGNED, updated.getStatus());
    assertEquals(QualityStatus.CLOSED, closed.getStatus());
  }

  @Test
  void shouldRegisterDocumentVersion() {
    InMemoryDocumentVersionRepository repository = new InMemoryDocumentVersionRepository();
    DocumentVersion version =
        DocumentVersion.current(
            "POD-001",
            1,
            Instant.parse("2026-06-29T18:00:00Z"),
            "dispatcher",
            "ABCDEF123456",
            Notes.of("Prima versione POD"));

    DocumentVersion saved =
        new DefaultRegisterDocumentVersionUseCase(repository)
            .handle(
                new it.gabriele.truckflow.application.port.in.RegisterDocumentVersionUseCase
                    .Command(version));

    assertTrue(saved.isCurrent());
    assertEquals(1, repository.findAll().size());
  }
}
