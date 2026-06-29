package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.port.in.StartWorkflowInstanceUseCase;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.workflow.WorkflowDefinition;
import it.gabriele.truckflow.domain.workflow.WorkflowInstance;
import it.gabriele.truckflow.domain.workflow.WorkflowStatus;
import it.gabriele.truckflow.domain.workflow.WorkflowStep;
import it.gabriele.truckflow.infrastructure.memory.InMemoryWorkflowDefinitionRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryWorkflowInstanceRepository;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class EnterpriseWorkflowOperationalUseCaseTest {

  @Test
  void shouldRegisterActivateStartMoveAndCompleteWorkflow() {
    InMemoryWorkflowDefinitionRepository definitionRepository =
        new InMemoryWorkflowDefinitionRepository();
    InMemoryWorkflowInstanceRepository instanceRepository =
        new InMemoryWorkflowInstanceRepository();
    WorkflowDefinition definition =
        WorkflowDefinition.draft(
            "CLAIM-FLOW",
            "Gestione claim cliente",
            List.of(
                WorkflowStep.of("OPEN", "Apertura", 1, false),
                WorkflowStep.of("REVIEW", "Revisione", 2, true),
                WorkflowStep.of("CLOSE", "Chiusura", 3, false)),
            Notes.of("Workflow claim"));

    new DefaultRegisterWorkflowDefinitionUseCase(definitionRepository)
        .handle(
            new it.gabriele.truckflow.application.port.in.RegisterWorkflowDefinitionUseCase.Command(
                definition));
    new DefaultActivateWorkflowDefinitionUseCase(definitionRepository)
        .handle(
            new it.gabriele.truckflow.application.port.in.ActivateWorkflowDefinitionUseCase.Command(
                "CLAIM-FLOW"));

    WorkflowInstance instance =
        new DefaultStartWorkflowInstanceUseCase(definitionRepository, instanceRepository)
            .handle(
                new StartWorkflowInstanceUseCase.Command(
                    "WF-001", "CLAIM-FLOW", Instant.parse("2026-06-29T09:00:00Z"), Notes.empty()));

    assertEquals("OPEN", instance.getCurrentStepCode());

    WorkflowInstance moved =
        new DefaultMoveWorkflowInstanceUseCase(instanceRepository)
            .handle(
                new it.gabriele.truckflow.application.port.in.MoveWorkflowInstanceUseCase.Command(
                    "WF-001", "REVIEW"));
    assertEquals("REVIEW", moved.getCurrentStepCode());

    WorkflowInstance completed =
        new DefaultCompleteWorkflowInstanceUseCase(instanceRepository)
            .handle(
                new it.gabriele.truckflow.application.port.in.CompleteWorkflowInstanceUseCase
                    .Command("WF-001", Instant.parse("2026-06-29T10:00:00Z")));
    assertEquals(WorkflowStatus.COMPLETED, completed.getStatus());
    assertTrue(instanceRepository.findById("WF-001").orElseThrow().getStatus().isTerminal());
  }
}
